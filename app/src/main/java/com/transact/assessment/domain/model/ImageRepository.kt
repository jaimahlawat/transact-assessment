package com.transact.assessment.domain.model

import com.transact.assessment.data.remote.dto.ImageInfoDTO

interface ImageRepository {
    suspend fun getImages() : Result<List<ImageInfoDTO>>
}