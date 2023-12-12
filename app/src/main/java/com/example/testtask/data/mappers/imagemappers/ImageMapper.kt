package com.example.testtask.data.mappers.imagemappers

import com.example.testtask.data.models.imageresponse.ImageOutResponse
import com.example.testtask.domain.model.ImageOut
import javax.inject.Inject

class ImageMapper @Inject constructor() {

    operator fun invoke(listResponse: List<ImageOutResponse>): List<ImageOut> {
        return listResponse.map { response ->
            ImageOut(
                id = response.id ?: 0,
                url = response.url.orEmpty(),
                date = response.date.toString().orEmpty(),
                lat = response.lat ?: 0.0,
                lng = response.lng ?: 0.0
            )
        }
    }
}