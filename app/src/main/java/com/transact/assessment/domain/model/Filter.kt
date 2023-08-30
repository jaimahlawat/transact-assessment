package com.transact.assessment.domain.model

data class Filter(
    val name: String,
    val type: String? = null,
    val isSelected: Boolean = false
)
