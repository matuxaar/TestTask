package com.example.testtask.data.mappers.usermappers

import com.example.testtask.data.models.userresponse.UserRequest
import com.example.testtask.domain.model.User
import javax.inject.Inject

class UserMapper @Inject constructor() {

    operator fun invoke(response: UserRequest): User =
        with(response) {
            return User(
                login = login.orEmpty(),
                password = password.orEmpty()
            )
        }
}