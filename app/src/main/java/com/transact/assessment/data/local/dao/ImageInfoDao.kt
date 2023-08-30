package com.transact.assessment.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.transact.assessment.data.local.entity.ImageInfoEntity

@Dao
interface ImageInfoDao {
    @Query("SELECT * FROM images")
    fun getAllImages(): PagingSource<Int, ImageInfoEntity>

    @Query("SELECT * FROM images WHERE author IS :query")
    fun getImagesByAuthor(query: String): PagingSource<Int, ImageInfoEntity>

    @Upsert
    suspend fun upsertAll(item: List<ImageInfoEntity>)

    @Query("DELETE FROM images")
    suspend fun clearAll()
}