package com.example.testtask.data.mappers.commentsmapper

import com.example.testtask.data.models.comment.CommentResponse
import com.example.testtask.domain.model.CommentOut
import javax.inject.Inject

class CommentsResponseMapper @Inject constructor() {

    operator fun invoke(
        comment: CommentOut
    ) : CommentResponse = with(comment) {
        return CommentResponse(
            id = id,
            date = date.toLong(),
            text = text
        )
    }
}