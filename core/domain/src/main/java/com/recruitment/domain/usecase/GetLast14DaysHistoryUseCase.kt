package com.recruitment.domain.usecase

import com.recruitment.domain.model.CurrencyCode
import com.recruitment.domain.model.RateHistoryPoint
import com.recruitment.domain.model.TableSource
import com.recruitment.domain.repository.RatesRepository

class GetLast14DaysHistoryUseCase(
    private val repository: RatesRepository
) {
    suspend operator fun invoke(
        code: CurrencyCode,
        table: TableSource,
        currentMid: Double
    ): Result<List<RateHistoryPoint>> =
        repository.getLast14DaysHistory(code)
}
