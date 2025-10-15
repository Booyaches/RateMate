package com.recruitment.network.model

/**
 * Represents a table of exchange rates from the network.
 *
 * @property table The table type, either "A" or "B".
 * @property effectiveDate The effective date of the rates in "yyyy-MM-dd" format.
 * @property rates The list of exchange rates in the table.
 */
data class TableDto(
    val table: String,
    val effectiveDate: String,
    val rates: List<RateDto>
)
