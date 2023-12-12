package com.example.testtask.data.mappers.commentsmapper

import com.example.testtask.data.models.comment.AddCommentRequest
import com.example.testtask.domain.model.CommentIn
import javax.inject.Inject

class CommentInResponseMapper @Inject constructor() {

    operator fun invoke(
        commentIn: CommentIn
    ) : AddCommentRequest {
        return AddCommentRequest(
            text = commentIn.text
        )
    }
}