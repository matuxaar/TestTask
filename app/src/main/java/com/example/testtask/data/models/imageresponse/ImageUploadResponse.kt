package com.example.testtask.data.models.imageresponse

import com.squareup.moshi.Json

data class ImageUploadResponse(
    @Json(name = "status") val status: Int? = null,
    @Json(name = "data") val data: ImageOutResponse? = null,
)