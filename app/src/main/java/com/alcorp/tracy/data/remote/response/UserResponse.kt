package com.alcorp.tracy.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("data")
    val data: DataUser? = null
)

data class DataUser(
    @field:SerializedName("token")
    val token: String? = null,

    @field:SerializedName("user")
    val user: User? = null,
)