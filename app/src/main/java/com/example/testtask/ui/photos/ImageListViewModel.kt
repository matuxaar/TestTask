package com.example.testtask.ui.photos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.testtask.data.source.ImageLoaderFactory
import com.example.testtask.domain.model.ImageOut
import com.example.testtask.domain.repositories.ImageRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageListViewModel @Inject constructor(
    private val imageRepository: ImageRepository,
    private val imageLoaderFactory: ImageLoaderFactory
) : ViewModel() {

    private var _imageSharedFlow =
        MutableSharedFlow<ImageOut>()
    val imageSharedFlow: SharedFlow<ImageOut> get() = _imageSharedFlow

    private val _imageFlow: MutableSharedFlow<PagingData<ImageOut>> = MutableSharedFlow()
    val imageFlow: SharedFlow<PagingData<ImageOut>> get() = _imageFlow


    private val _deleteImageLiveData = MutableLiveData<Boolean>()
    val deleteImageLiveData: LiveData<Boolean> get() = _deleteImageLiveData

    private val _imagesFromDb = MutableLiveData<List<ImageOut>>()
    val imageFromDb: LiveData<List<ImageOut>> get() = _imagesFromDb

    private val exceptionHandler = CoroutineExceptionHandler{_, e ->
        e.printStackTrace()
    }

    init {
        viewModelScope.launch {
            imageLoaderFactory.getImages().collect {
                _imageFlow.emit(it)
            }
        }
    }

    fun addPhoto(imageOut: ImageOut) {
        viewModelScope.launch(exceptionHandler) {
            _imageSharedFlow.emit(imageOut)
            imageRepository.addPhoto(imageOut)
        }
    }

    fun deleteImage(imageId: Int, imageOut: ImageOut) {
        viewModelScope.launch {
            try {
                val response = imageRepository.deleteImage(imageId)

                if (response.isSuccess) {
                    deleteImageFromDb(imageOut)
                    _deleteImageLiveData.value = response.isSuccess
                } else {
                    _deleteImageLiveData.value = response.isFailure
                }
            } catch (e: Exception) {
                _deleteImageLiveData.value = false
            }
        }
    }

    fun deleteImageFromDb(imageOut: ImageOut) {
        viewModelScope.launch(exceptionHandler) {
            imageRepository.deleteImageFromDb(imageOut)
        }
    }

}