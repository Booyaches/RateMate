package com.recruitment.data

import com.recruitment.database.model.CurrencyEntity
import com.recruitment.domain.model.Currency
import com.recruitment.domain.model.CurrencyCode
import com.recruitment.domain.model.RateHistoryPoint
import com.recruitment.domain.model.TableSource
import com.recruitment.network.model.HistoryDto
import com.recruitment.network.model.RateDto
import com.recruitment.network.model.TableDto
import java.time.LocalDate

/**
 * Converts a [TableDto] from the network layer to a list of [Currency] domain models.
 */
fun TableDto.toDomain(): List<Currency> =
    rates.map { it.toDomain(table = this.table, effectiveDate = this.effectiveDate) }

/**
 * Converts a [RateDto] from the network layer to a [Currency] domain model.
 *
 * @param table The table source for the currency rate.
 * @param effectiveDate The effective date of the currency rate.
 */
fun RateDto.toDomain(table: String, effectiveDate: String): Currency =
    Currency(
        code = CurrencyCode(code),
        name = currency,
        currentMid = mid,
        table = TableSource(table),
        effectiveDate = LocalDate.parse(effectiveDate)
    )

/**
 * Converts a [HistoryDto] from the network layer to a list of [RateHistoryPoint] domain models.
 * The list is sorted by date in descending order.
 *
 * @param currentMid The current middle exchange rate to compare against for the 10% deviation check.
 */
fun HistoryDto.toDomain(currentMid: Double): List<RateHistoryPoint> =
    rates.map {
        val date = LocalDate.parse(it.effectiveDate)
        val isAway = kotlin.math.abs(it.mid - currentMid) / currentMid > 0.10
        RateHistoryPoint(date = date, mid = it.mid, is10PctAwayFromCurrent = isAway)
    }.sortedByDescending { it.date }

