package com.transact.assessment.di

import androidx.room.Room
import com.google.gson.GsonBuilder
import com.transact.assessment.BuildConfig
import com.transact.assessment.common.Constants
import com.transact.assessment.common.Constants.BASE_URL
import com.transact.assessment.data.local.ImageDatabase
import com.transact.assessment.data.remote.ApiService
import com.transact.assessment.data.repository.ImageRepositoryImpl
import com.transact.assessment.domain.repository.ImageRepository
import com.transact.assessment.ui.home.HomeScreenViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            OkHttpClient().newBuilder().addInterceptor(interceptor).build()
        } else {
            OkHttpClient().newBuilder().build()
        }
    }

    single {
        GsonBuilder().create()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(get())
            .build()
    }

    single {
        get<Retrofit>().create(ApiService::class.java)
    }

    single {
        Room.databaseBuilder(
            androidApplication(),
            ImageDatabase::class.java,
            Constants.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    single {
        get<ImageDatabase>().imageInfoDAO()
    }

    singleOf(::ImageRepositoryImpl) { bind<ImageRepository>() }

    viewModelOf(::HomeScreenViewModel)
}