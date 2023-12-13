package com.example.testtask.data.mappers.usermappers

import com.example.testtask.data.models.userresponse.UserSignResponse
import com.example.testtask.domain.model.SignUpUser
import javax.inject.Inject

class SignUpResponseMapper @Inject constructor() {

    operator fun invoke(response: UserSignResponse): SignUpUser =
        with(response) {
            return SignUpUser(
                userId = userId ?: 0,
                login = login.orEmpty(),
                token = token.orEmpty()
            )
        }
}