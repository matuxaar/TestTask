package com.example.testtask.data.mappers.imagemappers

import com.example.testtask.data.models.imageresponse.ImageUploadResponse
import com.example.testtask.domain.model.ImageOut
import javax.inject.Inject

class ImageOutMapper @Inject constructor() {

    operator fun invoke(
        response: ImageUploadResponse
    ) : ImageOut {
        val image = response.data
        return ImageOut(
            id = image?.id ?: 0,
            url = image?.url.orEmpty(),
            date = image?.date.toString().orEmpty(),
            lat = image?.lat ?: 0.0,
            lng = image?.lng ?: 0.0
        )
    }
}