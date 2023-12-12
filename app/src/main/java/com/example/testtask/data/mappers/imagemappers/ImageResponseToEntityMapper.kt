package com.example.testtask.data.mappers.imagemappers

import com.example.testtask.data.database.ImageEntity
import com.example.testtask.data.models.imageresponse.ImageOutResponse
import javax.inject.Inject

class ImageResponseToEntityMapper @Inject constructor() {

    operator fun invoke(
        imageResponse: ImageOutResponse
    ): ImageEntity = with(imageResponse) {
        return ImageEntity(
            id = id ?: 0,
            url = url.orEmpty(),
            date = date ?: 0,
            lat = lat ?: 0.0,
            lng = lng ?: 0.0
        )
    }
}