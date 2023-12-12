package com.example.testtask.data.mappers.imagemappers

import com.example.testtask.data.models.imageresponse.ImageRequest
import com.example.testtask.domain.model.ImageIn
import javax.inject.Inject

class ImageInMapper @Inject constructor() {

    operator fun invoke(
        response: ImageRequest
    ) : ImageIn = with(response) {
        return ImageIn(
            base64Image = base64Image.orEmpty(),
            date = date ?: 0,
            lat = lat ?: 0.0,
            lng = lng ?: 0.0
        )
    }
}