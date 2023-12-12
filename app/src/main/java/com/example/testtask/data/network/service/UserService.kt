package com.example.testtask.data.network.service

import com.example.testtask.data.models.userresponse.SignUpUserResponse
import com.example.testtask.data.models.userresponse.UserRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("signup")
    suspend fun signUp(@Body userRequest: UserRequest): SignUpUserResponse

    @POST("signin")
    suspend fun signIn(@Body userRequest: UserRequest): SignUpUserResponse
}