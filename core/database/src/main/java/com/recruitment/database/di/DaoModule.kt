package com.recruitment.database.di

import com.recruitment.database.model.AppDb
import com.recruitment.database.model.CurrencyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    /**
     * Provides a singleton instance of the [CurrencyDao].
     *
     * @param db The [AppDb] instance.
     * @return A singleton [CurrencyDao] instance.
     */
    @Provides
    @Singleton
    fun provideCurrencyDao(db: AppDb): CurrencyDao {
        return db.currencyDao()
    }
    
}
