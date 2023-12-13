package com.example.testtask.data.mappers.commentsmapper

import com.example.testtask.data.database.CommentEntity
import com.example.testtask.domain.model.CommentOut
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class CommentsEntityMapper @Inject constructor(){

    operator fun invoke(
        entity: CommentEntity
    ): CommentOut = with(entity) {
        return CommentOut(
            id = id,
            date = mapTimestampToDate(date),
            text = text
        )
    }

    private fun mapTimestampToDate(timestamp: Long): String {
        val date = Date(timestamp)
        return SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(date)
    }
}