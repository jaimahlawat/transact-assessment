package com.transact.assessment.data.remote.dto

data class ImageInfoDto(
    val id: String,
    val author: String? = null,
    val width: Int = 0,
    val height: Int = 0,
    val url: String? = null,
    val download_url: String? = null
)