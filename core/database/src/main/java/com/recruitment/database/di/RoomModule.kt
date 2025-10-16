package com.recruitment.database.di

import android.content.Context
import androidx.room.Room
import com.recruitment.database.model.AppDb
import com.recruitment.database.model.CurrencyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for providing Room database-related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    /**
     * Provides a singleton instance of the [AppDb].
     *
     * @param context The application context.
     * @return A singleton [AppDb] instance.
     */
    @Provides
    @Singleton
    fun provideAppDb(@ApplicationContext context: Context): AppDb {
        return Room.databaseBuilder(
            context,
            AppDb::class.java,
            "app-db"
        ).build()
    }


}
