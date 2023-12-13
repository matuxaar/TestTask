package com.example.testtask.domain.model

data class ImageIn(
    val base64Image: String,
    val date: Long,
    val lat: Double,
    val lng: Double
)