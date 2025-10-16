package com.recruitment.domain.usecase

import com.recruitment.domain.repository.RatesRepository

class RefreshCurrenciesUseCase(
    private val repository: RatesRepository
) {
    suspend operator fun invoke(force: Boolean = false): Result<Unit> =
        repository.refreshLatestRates(force)
}