package com.recruitment.network.model

/**
 * Represents the historical data for a currency from the network.
 *
 * @property table The table type, which can be nullable.
 * @property currency The name of the currency.
 * @property code The currency code.
 * @property rates The list of historical exchange rates.
 */
data class HistoryDto(
    val table: String?,
    val currency: String,
    val code: String,
    val rates: List<HistoryRateDto>
)
