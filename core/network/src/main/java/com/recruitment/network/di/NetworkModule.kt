package com.recruitment.network.di

import com.recruitment.network.RetrofitFactory
import com.recruitment.network.api.NbpApi
import com.recruitment.network.datasource.NbpCurrencyDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideNbpApi(): NbpApi = RetrofitFactory.create()

    @Provides
    @Singleton
    fun provideRemoteDataSource(api: NbpApi): NbpCurrencyDataSource = NbpCurrencyDataSource(api)
}
