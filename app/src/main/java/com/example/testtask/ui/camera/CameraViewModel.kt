package com.example.testtask.ui.camera

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtask.domain.model.ImageIn
import com.example.testtask.domain.model.ImageOut
import com.example.testtask.domain.repositories.ImageRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class CameraViewModel @Inject constructor(
    private val repository: ImageRepository
): ViewModel() {

    private val _livedata = MutableLiveData<Result<ImageOut>>()
    val liveData: LiveData<Result<ImageOut>> get() = _livedata

    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> }

    fun uploadPhoto(imageIn: ImageIn) {
        viewModelScope.launch(exceptionHandler) {
            try {
                val result = repository.uploadImage(imageIn)
                _livedata.value = Result.success(result)
            } catch (e: Exception) {
                _livedata.value = Result.failure(e)
            }
        }
    }

}