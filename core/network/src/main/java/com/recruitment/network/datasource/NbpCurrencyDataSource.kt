package com.recruitment.network.datasource

import com.recruitment.network.api.NbpApi
import com.recruitment.network.common.NetworkResult
import com.recruitment.network.common.safeApiCall
import com.recruitment.network.model.HistoryDto
import com.recruitment.network.model.TableDto
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

/**
 * A data source for fetching currency data from the NBP API.
 *
 * @param api The [NbpApi] instance used to make network requests.
 */
class NbpCurrencyDataSource(private val api: NbpApi) {

    /**
     * Fetches both 'A' and 'B' tables of exchange rates concurrently and merges the results.
     *
     * @return A [NetworkResult] containing a merged list of [TableDto] objects from both tables on success, or an error on failure.
     */
    suspend fun getTablesAB(): NetworkResult<List<TableDto>> = safeApiCall {
        coroutineScope {
            val a = async { api.getTableA() }
            val b = async { api.getTableB() }
            a.await() + b.await()
        }
    }

    /**
     * Fetches the exchange rate history for a specific currency.
     *
     * @param table The table from which to fetch the currency history (e.g., "A" or "B").
     * @param code The currency code (e.g., "USD").
     * @param days The number of last days to fetch the history for.
     * @return A [NetworkResult] containing a [HistoryDto] with the currency's rate history on success, or an error on failure.
     */
    suspend fun getCurrencyHistory(
        table: String,
        code: String,
        days: Int
    ): NetworkResult<HistoryDto> =
        safeApiCall { api.getCurrencyHistory(table, code, days) }
}
