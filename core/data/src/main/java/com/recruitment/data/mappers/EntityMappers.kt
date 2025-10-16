package com.recruitment.data.mappers

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
