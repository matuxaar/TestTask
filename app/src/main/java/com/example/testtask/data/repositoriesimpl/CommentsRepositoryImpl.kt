package com.example.testtask.data.repositoriesimpl

import com.example.testtask.data.mappers.commentsmapper.CommentInResponseMapper
import com.example.testtask.data.mappers.commentsmapper.CommentResponseMapper
import com.example.testtask.data.mappers.commentsmapper.CommentsEntityMapper
import com.example.testtask.data.mappers.commentsmapper.CommentsListToEntityMapper
import com.example.testtask.data.mappers.commentsmapper.CommentsResponseMapper
import com.example.testtask.data.mappers.commentsmapper.CommentsToEntityMapper
import com.example.testtask.data.network.service.CommentService
import com.example.testtask.data.source.CommentDataSource
import com.example.testtask.domain.model.CommentIn
import com.example.testtask.domain.model.CommentOut
import com.example.testtask.domain.repositories.CommentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.IllegalStateException
import javax.inject.Inject

class CommentsRepositoryImpl @Inject constructor(
    private val database: CommentDataSource,
    private val commentService: CommentService,
    private val commentResponseMapper: CommentResponseMapper,
    private val commentsEntityMapper: CommentsEntityMapper,
    private val commentInResponseMapper: CommentInResponseMapper,
    private val commentsToEntityMapper: CommentsToEntityMapper,
    private val commentsListToEntityMapper: CommentsListToEntityMapper
) : CommentRepository {
    override suspend fun getComments(imageId: Int, page: Int): List<CommentOut> =
        withContext(Dispatchers.IO) {
            val response = commentService.getComments(imageId, page)
            val comments = response.data
            comments?.map { commentResponseMapper(it) } ?: throw IllegalStateException("Error")
        }

    override suspend fun deleteComment(commentId: Int, imageId: Int): Unit =
        withContext(Dispatchers.IO) {
            commentService.deleteComment(commentId, imageId)
        }

    override suspend fun addComment(commentIn: CommentIn, imageId: Int): CommentOut =
        withContext(Dispatchers.IO) {
            val response = commentInResponseMapper(commentIn)
            val comment = commentService.addComment(response, imageId)
            commentResponseMapper(comment.data)
        }

    override suspend fun getCommentsFromDb(): List<CommentOut> = withContext(Dispatchers.IO) {
        database.getCommentsFromDB().map { commentsEntityMapper(it) }
    }

    override suspend fun insertComment(comment: CommentOut) = withContext(Dispatchers.IO) {
        database.insertComment(commentsToEntityMapper(comment))
    }

    override suspend fun insertComments(comments: List<CommentOut>) =
        withContext(Dispatchers.IO) {
            database.insertComments(commentsListToEntityMapper(comments))
        }

    override suspend fun deleteCommentFromDb(comment: CommentOut) =
        withContext(Dispatchers.IO) {
            database.deleteComment(commentsToEntityMapper(comment))
        }
}