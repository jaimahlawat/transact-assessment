package com.transact.assessment.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.transact.assessment.data.local.ImageDatabase
import com.transact.assessment.data.remote.ApiService
import com.transact.assessment.data.remote.mediator.ImageRemoteMediator
import com.transact.assessment.domain.model.ImageRepository

@OptIn(ExperimentalPagingApi::class)
class ImageRepositoryImpl(
    private val imageDatabase: ImageDatabase,
    private val apiService: ApiService
) : ImageRepository {
    override fun getImages() = Pager(
        config = PagingConfig(10),
        remoteMediator = ImageRemoteMediator(
            imageDatabase, apiService
        ),
        pagingSourceFactory = {
            imageDatabase.imageInfoDAO().pagingSource()
        }
    ).flow
}