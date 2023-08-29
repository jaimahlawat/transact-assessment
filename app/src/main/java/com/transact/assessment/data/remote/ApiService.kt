package com.transact.assessment.data.remote

import com.transact.assessment.data.remote.dto.ImageInfoDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("list")
    suspend fun getImages(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): List<ImageInfoDTO>
}