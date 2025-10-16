package com.recruitment.database.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
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
     * Fetches a currency by its code.
     *
     * @param code The currency code.
     * @return A [CurrencyEntity] or null if not found.
     */
    @Query("SELECT * FROM currencies WHERE code = :code")
    suspend fun getByCode(code: String): CurrencyEntity?

    /**
     * Inserts or updates a list of currencies.
     */
    @Upsert
    suspend fun upsertAll(items: List<CurrencyEntity>)

    /**
     * Deletes all currencies from the database.
     */
    @Query("DELETE FROM currencies")
    suspend fun clear()
}
