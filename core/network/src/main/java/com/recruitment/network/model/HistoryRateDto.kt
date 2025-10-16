package com.recruitment.network.model

import com.squareup.moshi.JsonClass

/**
 * Represents a single historical exchange rate from the network.
 *
 * @property effectiveDate The effective date of the rate in "yyyy-MM-dd" format.
 * @property mid The middle exchange rate.
 */
@JsonClass(generateAdapter = true)
data class HistoryRateDto(
    val effectiveDate: String,
    val mid: Double
)
