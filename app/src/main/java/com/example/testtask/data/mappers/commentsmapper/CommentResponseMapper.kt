package com.example.testtask.data.mappers.commentsmapper

import com.example.testtask.data.models.comment.CommentResponse
import com.example.testtask.domain.model.CommentOut
import javax.inject.Inject

class CommentResponseMapper @Inject constructor() {

    operator fun invoke(
        response: CommentResponse
    ) : CommentOut = with(response) {
        return CommentOut(
            id = id ?: 0,
            date = date.toString().orEmpty(),
            text = text.orEmpty()
        )
    }
}