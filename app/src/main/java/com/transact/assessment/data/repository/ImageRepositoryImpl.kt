package com.transact.assessment.data.repository

import com.transact.assessment.data.remote.dto.ImageInfoDTO
import com.transact.assessment.domain.model.ImageRepository

class ImageRepositoryImpl() : ImageRepository {
    override suspend fun getImages(): Result<List<ImageInfoDTO>> {

        return TODO("Provide the return value")
    }
}