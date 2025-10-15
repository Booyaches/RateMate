package com.recruitment.network.model

/**
 * Represents a single exchange rate from the network.
 *
 * @property currency The name of the currency (e.g., "dolar amerykański").
 * @property code The currency code (e.g., "USD").
 * @property mid The middle exchange rate.
 */
data class RateDto(
    val currency: String,
    val code: String,
    val mid: Double
)
