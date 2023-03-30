package com.alcorp.tracy.di

import com.alcorp.tracy.data.AppRepository
import com.alcorp.tracy.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(): AppRepository {
        val apiService = ApiConfig.getApiService()
        return AppRepository.getInstance(apiService)
    }
}