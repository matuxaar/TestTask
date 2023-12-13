package com.example.testtask.data.mappers.commentsmapper

import com.example.testtask.data.database.CommentEntity
import com.example.testtask.domain.model.CommentOut
import javax.inject.Inject

class CommentsToEntityMapper @Inject constructor() {

    operator fun invoke(
        comment: CommentOut
    ): CommentEntity = with(comment) {
        return CommentEntity(
            id = id,
            date = date.toLong(),
            text = text
        )
    }
}