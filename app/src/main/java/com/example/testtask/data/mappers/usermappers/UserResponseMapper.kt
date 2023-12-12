package com.example.testtask.data.mappers.usermappers

import com.example.testtask.data.models.userresponse.UserRequest
import com.example.testtask.domain.model.User
import javax.inject.Inject

class UserResponseMapper @Inject constructor() {

    operator fun invoke(user: User): UserRequest =
        with(user) {
            return UserRequest(
                login = login,
                password = password
            )
        }
}
