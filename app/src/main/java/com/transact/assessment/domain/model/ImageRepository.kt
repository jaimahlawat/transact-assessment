package com.transact.assessment.domain.model

import androidx.paging.PagingData
import com.transact.assessment.data.local.entity.ImageInfoEntity
import com.transact.assessment.data.remote.dto.ImageInfoDTO
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    fun getImages() : Flow<PagingData<ImageInfoEntity>>
}