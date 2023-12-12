package com.example.testtask.data.mappers.imagemappers

import com.example.testtask.data.database.ImageEntity
import com.example.testtask.domain.model.ImageOut
import javax.inject.Inject

class ImageToEntityMapper @Inject constructor() {

    operator fun invoke(
        imageOut: ImageOut
    ) : ImageEntity = with(imageOut) {
        return ImageEntity(
            id = id,
            url = url,
            date = date.toLong(),
            lat = lat,
            lng = lng
        )
    }
}