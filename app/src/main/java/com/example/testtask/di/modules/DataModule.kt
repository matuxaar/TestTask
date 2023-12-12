package com.example.testtask.di.modules

import com.example.testtask.data.repositoriesimpl.ImageRepositoryImpl
import com.example.testtask.data.repositoriesimpl.UserRepositoryImpl
import com.example.testtask.domain.repositories.ImageRepository
import com.example.testtask.domain.repositories.UserRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @Binds
    fun getImageRepository(impl: ImageRepositoryImpl): ImageRepository

    @Binds
    fun getUserRepository(impl: UserRepositoryImpl): UserRepository
}