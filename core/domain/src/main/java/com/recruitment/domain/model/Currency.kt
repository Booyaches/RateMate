package com.recruitment.domain.model

import java.time.LocalDate

/**
 * Represents the code of a currency.
 *
 * @property value The currency code as a string.
 */
@JvmInline
value class CurrencyCode(val value: String)

/**
 * Represents the source table for an exchange rate.
 * The value must be either "A" or "B".
 *
 * @property value The table source as a string.
 */
@JvmInline
value class TableSource(val value: String) {
    init { require(value == "A" || value == "B") }
}

/**
 * Represents a currency with its exchange rate information.
 *
 * @property code The currency code.
 * @property name The name of the currency.
 * @property currentMid The current middle exchange rate.
 * @property table The source table of the exchange rate.
 * @property effectiveDate The date when the exchange rate is effective.
 */
data class Currency(
    val code: CurrencyCode,
    val name: String,
    val currentMid: Double,
    val table: TableSource,
    val effectiveDate: LocalDate
)
