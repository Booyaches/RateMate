package com.recruitment.domain.di

import com.recruitment.domain.repository.RatesRepository
import com.recruitment.domain.usecase.GetLast14DaysHistoryUseCase
import com.recruitment.domain.usecase.ObserveCurrenciesUseCase
import com.recruitment.domain.usecase.RefreshCurrenciesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    @Provides
    fun provideObserveCurrenciesUseCase(repository: RatesRepository) = ObserveCurrenciesUseCase(repository)

    @Provides
    fun provideRefreshCurrenciesUseCase(repository: RatesRepository) = RefreshCurrenciesUseCase(repository)

    @Provides
    fun provideGetLast14DaysHistoryUseCase(repository: RatesRepository) = GetLast14DaysHistoryUseCase(repository)
}