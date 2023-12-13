package com.example.testtask.data.models.imageresponse

import com.squareup.moshi.Json

data class ImageResponse(
    @Json(name = "status") val status: Int? = null,
    @Json(name = "data") val data: List<ImageOutResponse>? = null,
)