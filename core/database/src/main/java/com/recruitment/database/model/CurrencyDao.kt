package com.recruitment.database.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the currencies table.
 */
@Dao
interface CurrencyDao {
    /**
     * Observes a list of all currencies in the database, ordered by code.
     */
    @Query("SELECT * FROM currencies ORDER BY code ASC")
    fun observeAll(): Flow<List<CurrencyEntity>>

    /**
     * Returns a list of all currencies in the database.
     */
    @Query("SELECT * FROM currencies")
    suspend fun getAllOnce(): List<CurrencyEntity>

    /**
     * Inserts or updates a list of currencies.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(items: List<CurrencyEntity>)

    /**
     * Deletes all currencies from the database.
     */
    @Query("DELETE FROM currencies")
    suspend fun clear()
}
