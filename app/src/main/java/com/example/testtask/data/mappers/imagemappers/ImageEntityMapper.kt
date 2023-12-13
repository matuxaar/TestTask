package com.example.testtask.data.mappers.imagemappers

import com.example.testtask.data.database.ImageEntity
import com.example.testtask.domain.model.ImageOut
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class ImageEntityMapper @Inject constructor(){

    operator fun invoke(
        imageEntity: ImageEntity
    ): ImageOut = with(imageEntity) {
        return ImageOut(
            id = id,
            url = url,
            date = date,
            lat = lat,
            lng = lng
        )
    }
}