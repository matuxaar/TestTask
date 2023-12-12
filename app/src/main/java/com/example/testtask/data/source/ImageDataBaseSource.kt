package com.example.testtask.data.source

import com.example.testtask.data.database.ImageDao
import com.example.testtask.data.database.ImageEntity
import javax.inject.Inject

class ImageDataBaseSource @Inject constructor(
    private val imageDao: ImageDao
) {

    suspend fun getImagesFromDataBase(): List<ImageEntity> = imageDao.getImagesFromDataBase()

    suspend fun getImageById(id: Int): ImageEntity = imageDao.getImageById(id)

    suspend fun addPhoto(imageEntity: ImageEntity) = imageDao.addPhoto(imageEntity)

    fun getPagedPhotos(): List<ImageEntity> = imageDao.getPagedPhotos()

    suspend fun deleteImageFromDb(imageEntity: ImageEntity) = imageDao.deleteImageFromDb(imageEntity)

}