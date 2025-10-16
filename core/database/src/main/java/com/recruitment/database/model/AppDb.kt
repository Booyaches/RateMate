package com.recruitment.database.model

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * The Room database for the application.
 */
@Database(entities = [CurrencyEntity::class], version = 1)
abstract class AppDb : RoomDatabase() {
    /**
     * Returns the Data Access Object for the currencies table.
     */
    abstract fun currencyDao(): CurrencyDao
}
