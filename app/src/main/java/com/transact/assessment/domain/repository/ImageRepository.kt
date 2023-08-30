package com.transact.assessment.domain.repository

import androidx.paging.PagingData
import androidx.room.Query
import com.transact.assessment.data.local.entity.FilterEntity
import com.transact.assessment.data.local.entity.ImageInfoEntity
import com.transact.assessment.domain.model.Filter
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    fun getImages(query: String?): Flow<PagingData<ImageInfoEntity>>
    suspend fun updateFilter(filter: Filter?)

    val filters: Flow<List<Filter>>

    val selectedFilter: Flow<Filter?>
}