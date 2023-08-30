package com.transact.assessment.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.transact.assessment.data.local.entity.FilterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FilterDao {
    @Query("SELECT * FROM filters")
    fun getFilters(): Flow<List<FilterEntity>>

    @Query("SELECT * FROM filters where selected = 1 LIMIT 1")
    fun getCurrentFilterFlow(): Flow<FilterEntity?>

    @Query("SELECT * FROM filters where selected = 1")
    fun getCurrentFilters(): FilterEntity?

    @Upsert
    suspend fun upsertAll(item: List<FilterEntity>)

    @Upsert
    suspend fun upsertFilter(item: FilterEntity)

    @Query("DELETE FROM images")
    suspend fun clearAll()
}