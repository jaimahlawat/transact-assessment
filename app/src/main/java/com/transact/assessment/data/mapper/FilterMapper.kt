package com.transact.assessment.data.mapper

import com.transact.assessment.data.local.entity.FilterEntity
import com.transact.assessment.data.remote.dto.ImageInfoDto
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

fun ImageInfoDto.toFilterEntity(type: String) = FilterEntity(
    type = type,
    name = author ?: ""
)
