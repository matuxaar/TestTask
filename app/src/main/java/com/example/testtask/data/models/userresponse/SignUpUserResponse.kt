package com.example.testtask.data.models.userresponse

import com.squareup.moshi.Json

data class SignUpUserResponse(
    @Json(name = "status") val status: Int,
    @Json(name = "data") val data: UserSignResponse
)