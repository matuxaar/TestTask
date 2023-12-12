package com.example.testtask.ui.register

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

class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _liveData = MutableLiveData<SignUpUser>()
    val liveData: LiveData<SignUpUser> get() = _liveData

    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> }

    fun signUp(login: String, password: String) {
        viewModelScope.launch(exceptionHandler) {
            val signUpUser = userRepository.signUpUser(User(login, password))
            _liveData.value = signUpUser
        }
    }
}