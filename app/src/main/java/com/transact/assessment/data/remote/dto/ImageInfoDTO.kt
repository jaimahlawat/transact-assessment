package com.transact.assessment.data.remote.dto

data class ImageInfoDTO(
    val id: String? = null,
    val author: String? = null,
    val width: Int = 0,
    val height: Int = 0,
    val url: String? = null,
    val download_url: String? = null
)