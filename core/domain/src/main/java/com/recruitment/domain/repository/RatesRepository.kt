package com.recruitment.domain.repository

import com.recruitment.domain.model.Currency
import com.recruitment.domain.model.CurrencyCode
import com.recruitment.domain.model.RateHistoryPoint
import kotlinx.coroutines.flow.Flow

/**
 * Repository for managing currency exchange rates.
 */
interface RatesRepository {
    /**
     * Observes the latest currency exchange rates from the local data source.
     *
     * @return A [Flow] emitting a list of [Currency] objects.
     */
    fun observeLatestRates(): Flow<List<Currency>>

    /**
     * Refreshes the latest currency exchange rates from the remote data source.
     *
     * @param force If true, forces a refresh even if the data is not considered stale.
     * @return A [Result] indicating success or failure.
     */
    suspend fun refreshLatestRates(force: Boolean = false): Result<Unit>

    /**
     * Fetches the historical exchange rates for the last 14 days for a specific currency.
     *
     * @param code The code of the currency to fetch history for.
     * @return A [Result] containing a list of [RateHistoryPoint] objects.
     */
    suspend fun getLast14DaysHistory(
        code: CurrencyCode,
    ): Result<List<RateHistoryPoint>>
}
