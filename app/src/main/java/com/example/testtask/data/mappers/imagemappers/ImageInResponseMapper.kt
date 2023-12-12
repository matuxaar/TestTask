package com.example.testtask.data.mappers.imagemappers

import com.example.testtask.data.models.imageresponse.ImageRequest
import com.example.testtask.domain.model.ImageIn
import javax.inject.Inject

class ImageInResponseMapper @Inject constructor() {

    operator fun invoke(
        imageIn: ImageIn
    ) : ImageRequest = with (imageIn) {
        return ImageRequest(
            base64Image = base64Image,
            date = date,
            lat = lat,
            lng = lng
        )
    }
}