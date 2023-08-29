package com.transact.assessment.data.mappers

import com.transact.assessment.data.local.entity.ImageInfoEntity
import com.transact.assessment.data.remote.dto.ImageInfoDTO
import com.transact.assessment.domain.model.ImageInfo

fun ImageInfoDTO.toImageInfoEntity() = ImageInfoEntity(
    id = id,
    author = author,
    width = width,
    height = height,
    url = url,
    downloadUrl = download_url
)

fun ImageInfoEntity.toDomain() = ImageInfo(
    id = id,
    author = author,
    width = width,
    height = height,
    url = url,
    downloadUrl = downloadUrl
)