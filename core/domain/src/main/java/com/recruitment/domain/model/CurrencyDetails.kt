package com.recruitment.domain.model

/**
 * Represents the details of a currency, including its basic information and recent rate history.
 *
 * @property currency The basic information about the currency.
 * @property last14Days A list of rate history points for the last 14 days.
 */
data class CurrencyDetails(
    val currency: Currency,
    val last14Days: List<RateHistoryPoint>
)
