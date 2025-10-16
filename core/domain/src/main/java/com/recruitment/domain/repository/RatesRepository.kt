package com.recruitment.domain.repository

import com.recruitment.domain.model.Currency
import kotlinx.coroutines.flow.Flow

interface RatesRepository {
    fun observeLatestRates(): Flow<List<Currency>>
    suspend fun refreshLatestRates(force: Boolean = false): Result<Unit>
}