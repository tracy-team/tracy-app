package com.alcorp.tracy.data.remote.response

import com.google.gson.annotations.SerializedName

data class User (
    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("namaLengkap")
    val namaLengkap: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("noHp")
    val noHp: String? = null,

    @field:SerializedName("dateOfBirth")
    val dateOfBirth: String? = null,

    @field:SerializedName("alamat")
    val alamat: String? = null
)

data class UserLogin (
    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("password")
    val password: String? = null
)