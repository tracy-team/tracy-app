package com.alcorp.tracy.data.remote.response

import com.google.gson.annotations.SerializedName

data class MapResponse(
    @field:SerializedName("data")
    val data: DataMap? = null
)

data class DataMap(
    @field:SerializedName("namaKantor")
    val namaKantor: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("picture")
    val picture: String? = null,

    @field:SerializedName("alamat")
    val alamat: String? = null
)