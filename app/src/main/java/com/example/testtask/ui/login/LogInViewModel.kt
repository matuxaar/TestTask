package com.example.testtask.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtask.domain.model.SignUpUser
import com.example.testtask.domain.model.User
import com.example.testtask.domain.repositories.UserRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class LogInViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    private val _liveData = MutableLiveData<LogInState>()
    val liveData: LiveData<LogInState> get() = _liveData

    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> }

    fun logIn(login: String, password: String) {
        viewModelScope.launch(exceptionHandler) {
            _liveData.value = LogInState.InProgress
            try {
                val signUpUser = userRepository.loginUser(User(login, password))
                _liveData.value = LogInState.Success(signUpUser)
            } catch (e: Exception) {
                _liveData.value = LogInState.Error("Incorrect password or login")
            }
        }
    }

}