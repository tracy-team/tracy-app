package com.alcorp.tracy.data.remote.response

import com.google.gson.annotations.SerializedName

data class ReportListResponse(
    @field:SerializedName("data")
    val data: List<List<Report>>? = null
)

data class ListOfReport(
    val data: List<Report>? = null
)