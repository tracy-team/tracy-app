package com.alcorp.tracy.data.remote.response

import com.google.gson.annotations.SerializedName

data class ReportResponse(
    @field:SerializedName("data")
    val data: DataReport? = null
)

data class DataReport(
    @field:SerializedName("InsertedID")
    val InsertedID: String? = null
)