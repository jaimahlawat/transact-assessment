package com.transact.assessment.data.mapper

import com.transact.assessment.data.local.entity.FilterEntity
import com.transact.assessment.data.remote.dto.ImageInfoDTO
import com.transact.assessment.domain.model.Filter

fun FilterEntity.toDomain() = Filter(
    type = type,
    name = name,
    isSelected = isSelected
)

fun Filter.toEntity() = FilterEntity(
    type = type,
    name = name,
    isSelected = isSelected
)

fun ImageInfoDTO.toFilterEntity(type: String) = FilterEntity(
    type = type,
    name = author ?: ""
)
