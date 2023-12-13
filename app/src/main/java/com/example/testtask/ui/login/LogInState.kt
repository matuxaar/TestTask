package com.example.testtask.ui.login

import com.example.testtask.domain.model.SignUpUser

sealed class LogInState {

    object InProgress : LogInState()
    data class Success(val signUpUser: SignUpUser) : LogInState()
    data class Error(val errorMessage: String) : LogInState()

}