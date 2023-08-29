package com.transact.assessment.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.transact.assessment.common.Result
import com.transact.assessment.data.remote.ApiService
import com.transact.assessment.data.remote.dto.ImageInfoDTO

class ImagePagingSource(private val service: ApiService): PagingSource<Int, Result<List<ImageInfoDTO>>>() {
    override fun getRefreshKey(state: PagingState<Int, Result<List<ImageInfoDTO>>>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result<List<ImageInfoDTO>>> {

        return TODO("Provide the return value")
    }
}