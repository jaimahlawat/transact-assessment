package com.transact.assessment.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.transact.assessment.data.mappers.toDomain
import com.transact.assessment.domain.model.ImageRepository
import kotlinx.coroutines.flow.map

class HomeScreenViewModel(
    imageRepository: ImageRepository
): ViewModel() {

    val imageList = imageRepository
        .getImages()
        .map { pagingData ->
            pagingData.map { imageInfoEntity ->
                imageInfoEntity.toDomain()
            }
        }.cachedIn(viewModelScope)

}