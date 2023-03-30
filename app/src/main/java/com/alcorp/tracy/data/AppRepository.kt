package com.alcorp.tracy.data

import com.alcorp.tracy.data.remote.response.Map
import com.alcorp.tracy.data.remote.response.Report
import com.alcorp.tracy.data.remote.response.User
import com.alcorp.tracy.data.remote.response.UserLogin
import com.alcorp.tracy.data.remote.retrofit.ApiService

class AppRepository(private val apiService: ApiService) {
    fun regisUser(user: User) = apiService.regisUser(user)
    fun loginUser(user: UserLogin) = apiService.loginUser(user)
    fun createReport(token: String, report: Report) = apiService.createReport(token, report)
    fun createMap(token: String, map: Map) = apiService.createMap(token, map)
    fun getNearbyMap(token: String, lat: String, lng: String) = apiService.getNearbyMap(token, lat, lng)
    fun getListReport(token: String) = apiService.getListReport(token)

    companion object {
        @Volatile
        private var instance: AppRepository? = null
        fun getInstance(
            apiService: ApiService
        ): AppRepository =
            instance ?: synchronized(this) {
                instance ?: AppRepository(apiService)
            }.also { instance = it }
    }
}