package com.transact.assessment.domain.model

data class ImageInfo(
    val id: String? = null,
    val author: String? = null,
    val width: Int = 0,
    val height: Int = 0,
    val url: String? = null,
    val downloadUrl: String? = null
)