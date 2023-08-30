package com.transact.assessment.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.transact.assessment.data.mapper.toDomain
import com.transact.assessment.domain.model.Filter
import com.transact.assessment.domain.repository.ImageRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class HomeScreenViewModel(
    private val imageRepository: ImageRepository
): ViewModel() {

    val selectedFilter = imageRepository
        .selectedFilter
        .map {
            it?.name
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val imageList = selectedFilter.flatMapLatest { filterString ->
        imageRepository
            .getImages(filterString)
            .map { pagingData ->
                pagingData.map { imageInfoEntity ->
                    imageInfoEntity.toDomain()
                }
            }
    }.cachedIn(viewModelScope)

    val filters = imageRepository
        .filters
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun updateFilter(filter: Filter?) {
        viewModelScope.launch {
            imageRepository.updateFilter(filter)
        }
    }
}