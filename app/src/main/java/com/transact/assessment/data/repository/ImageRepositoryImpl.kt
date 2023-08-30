package com.transact.assessment.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.withTransaction
import com.transact.assessment.data.local.ImageDatabase
import com.transact.assessment.data.mapper.toDomain
import com.transact.assessment.data.mapper.toEntity
import com.transact.assessment.data.remote.ApiService
import com.transact.assessment.data.remote.mediator.ImageRemoteMediator
import com.transact.assessment.domain.model.Filter
import com.transact.assessment.domain.repository.ImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

@OptIn(ExperimentalPagingApi::class)
class ImageRepositoryImpl(
    private val imageDatabase: ImageDatabase,
    private val apiService: ApiService
) : ImageRepository {
    override fun getImages(query: String?) = Pager(
        config = PagingConfig(
            pageSize = 10,
            initialLoadSize = 10
        ),
        remoteMediator = ImageRemoteMediator(
            query,
            imageDatabase,
            apiService
        ),
        pagingSourceFactory = {
            if (query.isNullOrEmpty()) {
                imageDatabase.imageInfoDAO().pagingSource()
            } else {
                imageDatabase.imageInfoDAO().getImagesByAuthor(query)
            }

        }
    ).flow

    override val filters: Flow<List<Filter>> = imageDatabase.filterDAO().getFilters().map {
        it.map { filterEntity -> filterEntity.toDomain() }
    }
    override val selectedFilter: Flow<Filter?>
        get() = imageDatabase.filterDAO().getCurrentFilterFlow().map {
            it?.toDomain()
        }

    override suspend fun updateFilter(filter: Filter?) {
        withContext(Dispatchers.IO) {
            try {
                val filterDAO = imageDatabase.filterDAO()

                imageDatabase.withTransaction {
                    val currentFilter = filterDAO.getCurrentFilters()?.copy(isSelected = false)

                    if (currentFilter != null) {
                        filterDAO.upsertFilter(currentFilter)
                    }

                    if (filter != null) {
                        filterDAO.upsertFilter(filter.toEntity())
                    }
                }
            } catch (e: Exception) {
                Log.d("ImageRepositoryImpl", "Failed to update filter : ${e.message}")
            }
        }
    }
}