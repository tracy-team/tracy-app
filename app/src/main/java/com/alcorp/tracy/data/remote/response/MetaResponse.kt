package com.alcorp.tracy.data.remote.response

import com.google.gson.annotations.SerializedName

data class MetaResponse(
    @field:SerializedName("meta")
    val meta: Meta? = null
)

data class Meta(
    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("code")
    val code: Int? = null
)