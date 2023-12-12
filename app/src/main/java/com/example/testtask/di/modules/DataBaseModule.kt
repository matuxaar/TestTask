package com.example.testtask.di.modules

import android.content.Context
import androidx.room.Room
import com.example.testtask.data.database.AppDataBase
import com.example.testtask.data.database.CommentDao
import com.example.testtask.data.database.ImageDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {

    @Provides
    @Singleton
    fun provideDataBase(context: Context): AppDataBase {
        return Room.databaseBuilder(context, AppDataBase::class.java, DATABASE_NAME)
            .build()
    }

    @Provides
    @Singleton
    fun provideImageDao(db: AppDataBase): ImageDao = db.getImageDao()

    @Provides
    @Singleton
    fun provideCommentDao(db: AppDataBase): CommentDao = db.getCommentDao()

    companion object {
        private const val DATABASE_NAME = "database_name"
    }
}