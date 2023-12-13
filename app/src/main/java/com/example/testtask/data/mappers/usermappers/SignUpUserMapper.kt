package com.example.testtask.data.mappers.usermappers

import com.example.testtask.data.models.userresponse.UserSignResponse
import com.example.testtask.domain.model.SignUpUser
import javax.inject.Inject

class SignUpUserMapper @Inject constructor() {

    operator fun invoke(signUpUser: SignUpUser): UserSignResponse {
        return with(signUpUser) {
            UserSignResponse(
                userId = userId,
                login = login,
                token = token
            )
        }
    }
}