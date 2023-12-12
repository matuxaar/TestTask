package com.example.testtask.data.source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.testtask.data.paging.ImagePagingSource
import com.example.testtask.domain.repositories.ImageRepository
import javax.inject.Inject

class ImageLoaderFactory @Inject constructor(
    private val imageRepository: ImageRepository
) {

    val PAGE_SIZE = 20

    fun getImages() =
        Pager(config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false
        ),
            pagingSourceFactory = { ImagePagingSource(imageRepository) }
        ).flow
}