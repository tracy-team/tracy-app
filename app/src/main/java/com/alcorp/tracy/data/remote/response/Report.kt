package com.alcorp.tracy.data.remote.response

import com.google.gson.annotations.SerializedName

data class Report (
    @field:SerializedName("jenisKejahatan")
    val jenisKejahatan: String? = null,

    @field:SerializedName("uraian")
    val uraian: String? = null,

    @field:SerializedName("emailPolisi")
    val emailPolisi: String? = null,
)