package com.recruitment.domain.usecase

import com.recruitment.domain.model.Currency
import com.recruitment.domain.repository.RatesRepository
import kotlinx.coroutines.flow.Flow

class ObserveCurrenciesUseCase(
    private val repository: RatesRepository
) {
    operator fun invoke(): Flow<List<Currency>> = repository.observeLatestRates()
}