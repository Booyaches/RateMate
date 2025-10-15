package com.recruitment.data

import com.recruitment.database.model.CurrencyEntity
import com.recruitment.domain.model.Currency
import com.recruitment.domain.model.CurrencyCode
import com.recruitment.domain.model.TableSource
import java.time.LocalDate

/**
 * Converts a [CurrencyEntity] from the database to a [Currency] domain model.
 */

fun CurrencyEntity.toDomain() = Currency(
    code = CurrencyCode(code),
    name = name,
    currentMid = mid,
    table = TableSource(tableSource),
    effectiveDate = LocalDate.parse(effectiveDate)
)

fun mergePreferA(a: List<CurrencyEntity>, b: List<CurrencyEntity>): List<CurrencyEntity> {
    val map = HashMap<String, CurrencyEntity>()
    a.forEach { map[it.code] = it }
    b.forEach { map.putIfAbsent(it.code, it) }
    return map.values.toList()
}