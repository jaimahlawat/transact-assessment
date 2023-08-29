package com.transact.assessment.data.remote.dto

import com.transact.assessment.domain.model.ImageInfo

data class ImageInfoDTO(
    val id: String? = null,
    val author: String? = null,
    val width: Int = 0,
    val height: Int = 0,
    val url: String? = null,
    val download_url: String? = null
)

fun ImageInfoDTO.toDomain() = ImageInfo(
    id = this.id,
    author = this.author,
    width = this.width,
    height = this.height,
    url = this.url,
    downloadUrl = this.download_url
)