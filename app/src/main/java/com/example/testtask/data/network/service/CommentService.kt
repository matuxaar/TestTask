package com.example.testtask.data.network.service

import com.example.testtask.data.models.comment.AddCommentRequest
import com.example.testtask.data.models.comment.CommentListResponse
import com.example.testtask.data.models.comment.CommentOutResponse
import com.example.testtask.data.models.comment.CommentResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CommentService {

    @GET("image/{imageId}/comment")
    suspend fun getComments(
        @Path("imageId") imageId: Int,
        @Query("page") page: Int
    ): CommentListResponse

    @POST("image/{imageId}/comment")
    suspend fun addComment(
        @Body addCommentRequest: AddCommentRequest,
        @Path("imageId") imageId: Int
    ): CommentOutResponse

    @DELETE("image/{imageId}/comment/{commentId}")
    suspend fun deleteComment(
        @Path("imageId") imageId: Int,
        @Path("commentId") commentId: Int
    ): CommentOutResponse

}