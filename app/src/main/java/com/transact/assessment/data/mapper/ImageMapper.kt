package com.transact.assessment.data.mapper

import com.transact.assessment.data.local.entity.ImageInfoEntity
import com.transact.assessment.data.remote.dto.ImageInfoDto
import com.transact.assessment.domain.model.ImageInfo

fun ImageInfoDto.toImageInfoEntity() = ImageInfoEntity(
    id = id,
    author = author,
    width = width,
    height = height,
    url = url,
    downloadUrl = download_url
)

fun ImageInfoEntity.toDomain() = ImageInfo(
    author = author,
    downloadUrl = downloadUrl
)