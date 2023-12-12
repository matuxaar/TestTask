package com.example.testtask.domain.repositories

import androidx.paging.PagingData
import com.example.testtask.domain.model.ImageIn
import com.example.testtask.domain.model.ImageOut
import kotlinx.coroutines.flow.Flow

interface ImageRepository {

    suspend fun uploadImage(imageIn: ImageIn): ImageOut

    suspend fun getImages(page: Int): List<ImageOut>

    suspend fun deleteImage(imageId: Int): Result<Unit>

    suspend fun getImageById(id: Int): ImageOut

    suspend fun addPhoto(imageOut: ImageOut)

    fun getPagedPhotos(): List<ImageOut>

    suspend fun deleteImageFromDb(imageOut: ImageOut)
}