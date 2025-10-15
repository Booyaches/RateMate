package com.recruitment.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a currency in the database.
 *
 * @property code The currency code, used as the primary key.
 * @property name The name of the currency.
 * @property mid The middle exchange rate.
 * @property tableSource The source table of the exchange rate, either "A" or "B".
 * @property effectiveDate The effective date of the rate in "yyyy-MM-dd" format.
 * @property fetchedAt The timestamp when the data was fetched.
 */
@Entity(tableName = "currencies")
data class CurrencyEntity(
    @PrimaryKey val code: String,
    val name: String,
    val mid: Double,
    val tableSource: String,
    val effectiveDate: String,
    val fetchedAt: Long
)
