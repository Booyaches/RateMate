package com.recruitment.data.di

import com.recruitment.data.repository.RatesRepositoryImpl
import com.recruitment.database.model.AppDb
import com.recruitment.domain.repository.RatesRepository
import com.recruitment.network.datasource.NbpCurrencyDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for providing data layer dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    /**
     * Provides a singleton instance of the [RatesRepository].
     *
     * @param remote The remote data source for fetching currency data.
     * @param db The application's database.
     * @return A singleton instance of [RatesRepository].
     */
    @Provides
    @Singleton
    fun provideRatesRepository(
        remote: NbpCurrencyDataSource,
        db: AppDb
    ): RatesRepository = RatesRepositoryImpl(remote, db.currencyDao())
}
