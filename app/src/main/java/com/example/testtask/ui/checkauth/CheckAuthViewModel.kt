package com.example.testtask.ui.checkauth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtask.domain.repositories.UserRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class CheckAuthViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel(){

    private val _liveData = MutableLiveData<Boolean>()
    val liveData: LiveData<Boolean> = _liveData

    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> }


    fun checkAuth() {
        viewModelScope.launch(exceptionHandler) {
            _liveData.value = userRepository.checkAuth()
        }
    }
}