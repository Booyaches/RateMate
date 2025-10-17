package com.recruitment.data.repository

import com.recruitment.database.model.CurrencyDao
import com.recruitment.database.model.CurrencyEntity
import com.recruitment.domain.model.CurrencyCode
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

    @Test
    fun `get14getLast14DaysHistory calls api when currency found in db`() = runTest {
        coEvery { dao.getByCode("PLN") } returns CurrencyEntity(
            code = "PLN",
            name = "złoty",
            mid = 0.234,
            tableSource = "A",
            fetchedAt = System.currentTimeMillis(),
            effectiveDate = ""
        )

        repository.getLast14DaysHistory(CurrencyCode("PLN"))

        coVerify { remote.getCurrencyHistory(any(), any(), any()) }
    }

    @Test
    fun `get14getLast14DaysHistory failes when currency not found in db`() = runTest {
        coEvery { dao.getByCode("PLN") } returns null

        val result = repository.getLast14DaysHistory(CurrencyCode("PLN"))
        assert(result.isFailure)

        coVerify(exactly = 0) { remote.getCurrencyHistory(any(), any(), any()) }
    }
}





