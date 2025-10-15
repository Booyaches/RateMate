package com.recruitment.data

import com.recruitment.domain.model.Currency
import com.recruitment.domain.model.CurrencyCode
import com.recruitment.domain.model.RateHistoryPoint
import com.recruitment.domain.model.TableSource
import com.recruitment.network.model.HistoryDto
import com.recruitment.network.model.RateDto
import com.recruitment.network.model.TableDto
import java.time.LocalDate

fun TableDto.toDomain(): List<Currency> =
    rates.map { it.toDomain(table = this.table, effectiveDate = this.effectiveDate) }

fun RateDto.toDomain(table: String, effectiveDate: String): Currency =
    Currency(
        code = CurrencyCode(code),
        name = currency,
        currentMid = mid,
        table = TableSource(table),
        effectiveDate = LocalDate.parse(effectiveDate)
    )

fun HistoryDto.toDomain(currentMid: Double): List<RateHistoryPoint> =
    rates.map {
        val date = LocalDate.parse(it.effectiveDate)
        val isAway = kotlin.math.abs(it.mid - currentMid) / currentMid > 0.10
        RateHistoryPoint(date = date, mid = it.mid, is10PctAwayFromCurrent = isAway)
    }.sortedByDescending { it.date }
