package com.example.testtask.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ImageDao {

    @Query("SELECT * FROM image_table")
    suspend fun getImagesFromDataBase(): List<ImageEntity>

    @Query("SELECT * FROM image_table WHERE id = :id")
    suspend fun getImageById(id: Int): ImageEntity

    @Insert
    suspend fun addPhotos(imageEntityList: List<ImageEntity>)

    @Query("SELECT * FROM image_table ORDER BY id")
    fun getPagedPhotos(): List<ImageEntity>

    @Delete
    suspend fun deleteImageFromDb(imageEntity: ImageEntity)
}