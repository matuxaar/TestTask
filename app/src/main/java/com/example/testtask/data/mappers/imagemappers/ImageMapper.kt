package com.example.testtask.data.mappers.imagemappers

import com.example.testtask.data.models.imageresponse.ImageOutResponse
import com.example.testtask.domain.model.ImageOut
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class ImageMapper @Inject constructor() {

    operator fun invoke(listResponse: List<ImageOutResponse>): List<ImageOut> {
        return listResponse.map { response ->
            ImageOut(
                id = response.id ?: 0,
                url = response.url.orEmpty(),
                date = mapTimestampToDate(response.date!!),
                lat = response.lat ?: 0.0,
                lng = response.lng ?: 0.0
            )
        }
    }

    private fun mapTimestampToDate(timestamp: Long): String {
        val date = Date(timestamp * 1000)
        return SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(date)
    }
}