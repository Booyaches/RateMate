package com.recruitment.data.repository

import com.recruitment.data.mappers.toDomain
import com.recruitment.data.mappers.toEntity
import com.recruitment.database.model.CurrencyDao
import com.recruitment.domain.model.Currency
import com.recruitment.domain.repository.RatesRepository
import com.recruitment.network.common.NetworkResult
import com.recruitment.network.datasource.NbpCurrencyDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RatesRepositoryImpl(
    private val remote: NbpCurrencyDataSource,
    private val dao: CurrencyDao,
    private val gracePeriod: Long = 60000L
) : RatesRepository {

    override fun observeLatestRates(): Flow<List<Currency>> =
        dao.observeAll().map { list -> list.map { it.toDomain() } }

    override suspend fun refreshLatestRates(force: Boolean): Result<Unit> = runCatching {
        val now = System.currentTimeMillis()
        if (!force) {
            val local = dao.getAllOnce()
            val fresh = local.firstOrNull()?.let { now - it.fetchedAt < gracePeriod } == true
            if (fresh) return@runCatching
        }

        when (val res = remote.getTablesAB()) {
            is NetworkResult.Success -> {
                val tables = res.data
                val tableA = tables.firstOrNull { it.table == "A" }?.toEntity(now).orEmpty() //TODO Remove magic letters
                val tableB = tables.firstOrNull { it.table == "B" }?.toEntity(now).orEmpty()
                val merged = tableA + tableB
                dao.upsertAll(merged)
            }

            is NetworkResult.Error -> throw IllegalStateException("Network error ${res.error}") //TODO map to domain error
            NetworkResult.Loading -> Unit
        }
    }
}