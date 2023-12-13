package com.example.testtask.domain.repositories

import com.example.testtask.domain.model.SignUpUser
import com.example.testtask.domain.model.User

interface UserRepository {

    suspend fun signUpUser(user: User): SignUpUser

    suspend fun loginUser(user: User): SignUpUser

    suspend fun checkAuth(): Boolean

}