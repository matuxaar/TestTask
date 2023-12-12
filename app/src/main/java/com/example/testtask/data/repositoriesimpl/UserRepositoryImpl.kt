package com.example.testtask.data.repositoriesimpl

import com.example.testtask.data.mappers.usermappers.SignUpResponseMapper
import com.example.testtask.data.models.userresponse.UserRequest
import com.example.testtask.data.network.service.UserService
import com.example.testtask.data.source.UserDataSource
import com.example.testtask.domain.model.SignUpUser
import com.example.testtask.domain.model.User
import com.example.testtask.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val signUpResponseMapper: SignUpResponseMapper,
    private val userService: UserService,
    private val sharedPreferences: UserDataSource
) : UserRepository {

    override suspend fun signUpUser(user: User): SignUpUser {
        val userRequest = UserRequest(user.login, user.password)
        val signUpResponse = userService.signUp(userRequest)
        val userSign = signUpResponseMapper(signUpResponse.data)
        sharedPreferences.setToken(userSign.token)
        return SignUpUser(userSign.login, userSign.token, userSign.userId)
    }

    override suspend fun loginUser(user: User): SignUpUser {
        val userRequest = UserRequest(user.login, user.password)
        val signUpResponse = userService.signIn(userRequest)
        val userSign = signUpResponseMapper(signUpResponse.data)
        sharedPreferences.setToken(userSign.token)
        return SignUpUser(userSign.login, userSign.token, userSign.userId)
    }

    override suspend fun checkAuth(): Boolean = withContext(Dispatchers.IO) {
        val token = sharedPreferences.getToken()
        token.isNotEmpty()
    }

}