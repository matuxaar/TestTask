package com.example.testtask.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "comment_table"
)
data class CommentEntity(
    @PrimaryKey
    @ColumnInfo val id: Int,
    @ColumnInfo val date: Long,
    @ColumnInfo val text: String
)
