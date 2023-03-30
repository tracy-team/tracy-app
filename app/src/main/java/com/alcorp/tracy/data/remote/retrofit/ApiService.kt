package com.alcorp.tracy.data.remote.retrofit

import com.alcorp.tracy.data.remote.response.*
import com.alcorp.tracy.data.remote.response.Map
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("auth/users/register")
    fun regisUser(@Body user: User): Call<MetaResponse>

    @POST("auth/users/login")
    fun loginUser(@Body user: UserLogin): Call<UserResponse>

    @POST("report/create")
    fun createReport(
        @Header("Authorization") token: String,
        @Body report: Report
    ): Call<ReportResponse>

    @POST("lokasi/save")
    fun createMap(
        @Header("Authorization") token: String,
        @Body map: Map
    ): Call<MetaResponse>

    @GET("maps/nearby")
    fun getNearbyMap(
        @Header("Authorization") token: String,
        @Query("lat") lat: String,
        @Query("lng") lng: String
    ): Call<MapResponse>

    @POST("report/current/all")
    fun getListReport(
        @Header("Authorization") token: String
    ): Call<TestResponse>
}