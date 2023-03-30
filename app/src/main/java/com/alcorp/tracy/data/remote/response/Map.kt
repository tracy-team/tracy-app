package com.alcorp.tracy.data.remote.response

import com.google.gson.annotations.SerializedName

data class Map (
    @field:SerializedName("idLaporan")
    val idLaporan: String? = null,

    @field:SerializedName("latitude")
    val latitude: String? = null,

    @field:SerializedName("longitude")
    val longitude: String? = null,

    @field:SerializedName("detailLokasi")
    val detailLokasi: String? = null
)