package com.transact.assessment.data.remote.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.transact.assessment.data.local.ImageDatabase
import com.transact.assessment.data.local.entity.ImageInfoEntity
import com.transact.assessment.data.local.entity.RemoteKeysEntity
import com.transact.assessment.data.mapper.toFilterEntity
import com.transact.assessment.data.mapper.toImageInfoEntity
import com.transact.assessment.data.remote.ApiService

@OptIn(ExperimentalPagingApi::class)
class ImageRemoteMediator(
    private val query: String?,
    private val imageDatabase: ImageDatabase,
    private val apiService: ApiService
): RemoteMediator<Int, ImageInfoEntity>() {

    private val imageInfoDao = imageDatabase.imageInfoDAO()
    private val filterDAO = imageDatabase.filterDAO()
    private val remoteKeysDao = imageDatabase.remoteKeysDao()

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ImageInfoEntity>
    ): MediatorResult {
        return try {
            val loadKey = when(loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevKey = remoteKeys?.prevKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    prevKey
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKeys?.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    nextKey
                }
            }

            if (query != null) {
                return MediatorResult.Success(
                    endOfPaginationReached = true
                )
            }

            val images = apiService.getImages(
                page = loadKey,
                limit = state.config.pageSize
            )

            val endOfPaginationReached = images.isEmpty()

            imageDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeysDao.clearRemoteKeys()
                    imageInfoDao.clearAll()
                }

                val nextKey = if (endOfPaginationReached) null else loadKey.plus(1)
                val prevKey = if (loadKey == 1) null else loadKey - 1

                val keys = images.map {
                    RemoteKeysEntity(repoId = it.id, prevKey = prevKey, nextKey = nextKey)
                }

                remoteKeysDao.insertAll(keys)
                imageInfoDao.upsertAll(images.map { it.toImageInfoEntity() })

                val currentFilter = filterDAO.getCurrentFilters()
                val filterList = currentFilter?.let {
                    images.map {
                        it.toFilterEntity("Author").copy(isSelected = currentFilter.name == it.author)
                    }
                } ?: images.map { it.toFilterEntity("Author") }

                filterDAO.upsertAll(filterList)
            }

            MediatorResult.Success(
                endOfPaginationReached = endOfPaginationReached
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ImageInfoEntity>): RemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                imageDatabase.remoteKeysDao().remoteKeysRepoId(repo.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, ImageInfoEntity>
    ): RemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                imageDatabase.remoteKeysDao().remoteKeysRepoId(repoId)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ImageInfoEntity>): RemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                imageDatabase.remoteKeysDao().remoteKeysRepoId(repo.id)
            }
    }
}