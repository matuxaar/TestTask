package com.example.testtask.data.models.imageresponse

import com.squareup.moshi.Json

data class ImageDtoInRequest(
    @Json(name = "ImageDtoIn") val imageDtoIn: ImageRequest? = null
)