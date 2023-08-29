package com.transact.assessment.di

import com.transact.assessment.common.Constants.BASE_URL
import com.transact.assessment.data.remote.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            OkHttpClient.Builder().addNetworkInterceptor(interceptor).build()
        } else {
            OkHttpClient.Builder().build()
        }
    }
    single { GsonConverterFactory.create() }
    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(BASE_URL)
            .addConverterFactory(get())
            .build()
    }

    single {
        get<Retrofit>().create(ApiService::class.java)
    }
}