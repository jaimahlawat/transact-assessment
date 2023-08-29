package com.transact.assessment.data.remote.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.transact.assessment.data.local.ImageDatabase
import com.transact.assessment.data.local.entity.ImageInfoEntity
import com.transact.assessment.data.mappers.toImageInfoEntity
import com.transact.assessment.data.remote.ApiService

@OptIn(ExperimentalPagingApi::class)
class ImageRemoteMediator(
    private val imageDatabase: ImageDatabase,
    private val apiService: ApiService
): RemoteMediator<Int, ImageInfoEntity>() {

    private val imageInfoDao = imageDatabase.imageInfoDAO()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ImageInfoEntity>
    ): MediatorResult {
        return try {
            val loadKey = when(loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success( true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    } else {
                        val lastItemId = lastItem.id?.toInt()?.plus(1)
                        val nextPage = lastItemId?.div(state.config.pageSize)?.plus(1)
                        nextPage ?: 1
                    }
                }
            }

            val images = apiService.getImages(
                page = loadKey,
                limit = state.config.pageSize
            )

            imageDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    imageInfoDao.clearAll()
                }

                val imageInfoEntity = images.map {
                    it.toImageInfoEntity()
                }

                imageInfoDao.upsertAll(imageInfoEntity)
            }

            MediatorResult.Success(
                endOfPaginationReached = images.isEmpty()
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}