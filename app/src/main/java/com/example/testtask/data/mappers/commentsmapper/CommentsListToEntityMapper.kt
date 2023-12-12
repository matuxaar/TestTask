package com.example.testtask.data.mappers.commentsmapper

import com.example.testtask.data.database.CommentEntity
import com.example.testtask.domain.model.CommentOut
import javax.inject.Inject

class CommentsListToEntityMapper @Inject constructor() {

    operator fun invoke(
        comments: List<CommentOut>
    ): List<CommentEntity> = comments.map {
        CommentEntity(
            id = it.id,
            date = it.date.toLong(),
            text = it.text
        )
    }
}