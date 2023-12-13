package com.example.testtask.domain.repositories

import com.example.testtask.domain.model.ImageIn
import com.example.testtask.domain.model.ImageOut

interface ImageRepository {

    suspend fun uploadImage(imageIn: ImageIn): ImageOut

    suspend fun getImages(page: Int): List<ImageOut>

    suspend fun deleteImage(imageId: Int): Result<Unit>

    suspend fun getImageById(id: Int): ImageOut

    fun getPagedPhotos(): List<ImageOut>

    suspend fun deleteImageFromDb(imageOut: ImageOut)
}