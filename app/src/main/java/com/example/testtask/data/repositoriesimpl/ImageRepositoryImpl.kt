package com.example.testtask.data.repositoriesimpl

import com.example.testtask.data.mappers.imagemappers.ImageEntityMapper
import com.example.testtask.data.mappers.imagemappers.ImageInResponseMapper
import com.example.testtask.data.mappers.imagemappers.ImageMapper
import com.example.testtask.data.mappers.imagemappers.ImageOutMapper
import com.example.testtask.data.mappers.imagemappers.ImageToEntityMapper
import com.example.testtask.data.network.service.ImageService
import com.example.testtask.data.source.ImageDataBaseSource
import com.example.testtask.domain.model.ImageIn
import com.example.testtask.domain.model.ImageOut
import com.example.testtask.domain.repositories.ImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val imageDataBaseSource: ImageDataBaseSource,
    private val imageService: ImageService,
    private val imageEntityMapper: ImageEntityMapper,
    private val imageToEntityMapper: ImageToEntityMapper,
    private val imageMapper: ImageMapper,
    private val imageInResponseMapper: ImageInResponseMapper,
    private val imageOutMapper: ImageOutMapper
) : ImageRepository {

    override suspend fun uploadImage(imageIn: ImageIn): ImageOut = withContext(Dispatchers.IO) {
        val uploadImageResponse = imageInResponseMapper(imageIn)
        val response = imageService.uploadImage(uploadImageResponse)
        val imageResp = response.body()
        imageOutMapper(imageResp ?: throw IllegalStateException("ImageOutResponse is null"))
    }

    override suspend fun getImages(page: Int): List<ImageOut> {
        val response = imageService.getImage(page)

        if (response.isSuccessful) {
            val imageResponse = response.body()
            return imageMapper(imageResponse?.data.orEmpty())
        } else {
            return emptyList()
        }
    }

    override suspend fun deleteImage(imageId: Int): Result<Unit> {
        return try {
            val response = imageService.deleteImage(imageId)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to delete image"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getImageById(id: Int): ImageOut = withContext(Dispatchers.IO) {
        imageEntityMapper(imageDataBaseSource.getImageById(id))
    }

    override suspend fun addPhoto(imageOut: ImageOut) = withContext(Dispatchers.IO) {
        imageDataBaseSource.addPhoto(imageToEntityMapper(imageOut))
    }

    override fun getPagedPhotos(): List<ImageOut> {
        return imageDataBaseSource.getPagedPhotos().map { imageEntityMapper(it) }
    }

    override suspend fun deleteImageFromDb(imageOut: ImageOut) =
        withContext(Dispatchers.IO) {
            imageDataBaseSource.deleteImageFromDb(imageToEntityMapper(imageOut))
        }

}