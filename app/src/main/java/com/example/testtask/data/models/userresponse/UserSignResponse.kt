package com.example.testtask.data.models.userresponse

import com.squareup.moshi.Json

data class UserSignResponse (
    @Json(name = "usedId") val userId: Int? = null,
    @Json(name = "login") val login: String? = null,
    @Json(name = "token") val token: String? = null,
)