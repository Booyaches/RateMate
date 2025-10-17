package com.recruitment.data.repository

import com.recruitment.database.model.CurrencyDao
import com.recruitment.database.model.CurrencyEntity
import com.recruitment.network.common.NetworkError
import com.recruitment.network.common.NetworkResult
import com.recruitment.network.datasource.NbpCurrencyDataSource
import com.recruitment.network.model.RateDto
import com.recruitment.network.model.TableDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.Instant

class RatesRepositoryImplTest {

    private lateinit var repository: RatesRepositoryImpl
    private val dao = mockk<CurrencyDao>(relaxed = true)
    private val remote = mockk<NbpCurrencyDataSource>()

    private lateinit var tableA: TableDto
    private lateinit var tableB: TableDto


    @Before
    fun setup() {
        repository = RatesRepositoryImpl(remote = remote, dao = dao)
        tableA = TableDto("A", Instant.now().toString(), listOf(RateDto("złoty", "PLN", 0.245)))
        tableB = TableDto("B", Instant.now().toString(), listOf(RateDto("złoty", "PLN", 0.245)))
    }


    @Test
    fun `refreshLatestRates should upsert data on success`() = runTest {
        coEvery { remote.getTablesAB() } returns NetworkResult.Success(listOf(tableA, tableB))

        repository.refreshLatestRates()

        coVerify { dao.upsertAll(any()) }
    }

    @Test
    fun `refreshLatestRates should not upsert data on failure`() = runTest {
        coEvery { remote.getTablesAB() } returns NetworkResult.Error(NetworkError.Unknown())

        repository.refreshLatestRates()

        coVerify(exactly = 0) { dao.upsertAll(any()) }
    }

    @Test
    fun `refreshLatestRates should not be called when gracePeriod below 60 secs`() = runTest {
        coEvery { dao.getAllOnce() } returns listOf(
            CurrencyEntity(
                code = "PLN",
                name = "złoty",
                mid = 0.234,
                tableSource = "A",
                fetchedAt = System.currentTimeMillis(),
                effectiveDate = ""
            )
        )

        repository.refreshLatestRates()

        coVerify(exactly = 0) { remote.getTablesAB() }
    }

}





