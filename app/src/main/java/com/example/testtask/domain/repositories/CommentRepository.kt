package com.example.testtask.domain.repositories

import com.example.testtask.domain.model.CommentIn
import com.example.testtask.domain.model.CommentOut

interface CommentRepository {

    suspend fun getComments(imageId: Int, page: Int): List<CommentOut>

    suspend fun deleteComment(commentId: Int, imageId: Int)

    suspend fun addComment(commentIn: CommentIn, imageId: Int): CommentOut

    suspend fun getCommentsFromDb(): List<CommentOut>

    suspend fun insertComment(comment: CommentOut)

    suspend fun insertComments(comment: List<CommentOut>)

    suspend fun deleteCommentFromDb(comment: CommentOut)
}