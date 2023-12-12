package com.example.testtask.data.models.comment

import com.squareup.moshi.Json

data class CommentListResponse(
    @Json(name = "status") val status: Int? = null,
    @Json(name = "data") val data: List<CommentResponse>? = null
)