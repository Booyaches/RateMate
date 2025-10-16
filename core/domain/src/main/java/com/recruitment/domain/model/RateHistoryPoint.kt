package com.recruitment.domain.model

import java.time.LocalDate

/**
 * Represents a single point in the history of a currency exchange rate.
 *
 * This data class is used to store the average exchange rate for a specific day.
 * It also includes a flag to indicate if the rate on that day deviates significantly
 * from a reference (e.g., current) rate.
 *
 * @property date The date for which the exchange rate was recorded.
 * @property mid The average exchange rate (middle rate) for the given day.
 * @property is10PctAwayFromCurrent A boolean flag indicating whether the [mid] rate
 *           differs by more than 10% from the current average rate.
 *           It is `true` if the difference is greater than 10%, otherwise `false`.
 *           Compliant to recruitment task.
 */

data class RateHistoryPoint(
    val date: LocalDate,
    val mid: Double,
    val is10PctAwayFromCurrent: Boolean
)