package com.recruitment.network.api

import com.recruitment.network.model.HistoryDto
import com.recruitment.network.model.TableDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit interface for the NBP (Narodowy Bank Polski) API.
 * SOURCE: https://api.nbp.pl/
 */
interface NbpApi {


    /**
     * Fetches the 'A' table of exchange rates.
     *
     * @param format The response format, defaults to "json".
     * @return A list of [TableDto] objects.
     */
    @GET("exchangerates/tables/A")
    suspend fun getTableA(
        @Query("format") format: String = "json"
    ): List<TableDto>

    /**
     * Fetches the 'B' table of exchange rates.
     *
     * @param format The response format, defaults to "json".
     * @return A list of [TableDto] objects.
     */
    @GET("exchangerates/tables/B")
    suspend fun getTableB(
        @Query("format") format: String = "json"
    ): List<TableDto>

    /**
     * Fetches the exchange rate history for a specific currency for a given number of days.
     *
     * @param table The table from which to fetch the currency history (e.g., "A" or "B").
     * @param code The currency code (e.g., "USD").
     * @param days The number of last days to fetch the history for.
     * @param format The response format, defaults to "json".
     * @return A [HistoryDto] object containing the currency's rate history.
     */
    @GET("exchangerates/rates/{table}/{code}/last/{days}")
    suspend fun getCurrencyHistory(
        @Path("table") table: String,
        @Path("code") code: String,
        @Path("days") days: Int,
        @Query("format") format: String = "json"
    ): HistoryDto
}