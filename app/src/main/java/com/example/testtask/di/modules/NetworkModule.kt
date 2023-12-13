package com.example.testtask.di.modules

import com.example.testtask.data.network.resultcallfactory.ResultCallAdapterFactory
import com.example.testtask.data.network.service.CommentService
import com.example.testtask.data.network.service.ImageService
import com.example.testtask.data.network.service.UserService
import com.example.testtask.data.source.UserDataSource
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun get(token: UserDataSource): Retrofit {
        val interceptor = TokenInterceptor(token)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        return Retrofit.Builder()
            .baseUrl("https://junior.balinasoft.com/api/")
            .addConverterFactory(MoshiConverterFactory.create(getMoshi()))
            .addCallAdapterFactory(ResultCallAdapterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    @Named("retrofitUser")
    fun getUser(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://junior.balinasoft.com/api/account/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(ResultCallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun getMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun getUserService(@Named("retrofitUser") retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun getCommentService(retrofit: Retrofit): CommentService {
        return retrofit.create(CommentService::class.java)
    }

    @Provides
    @Singleton
    fun getPhotoService(retrofit: Retrofit): ImageService {
        return retrofit.create(ImageService::class.java)
    }

}

class TokenInterceptor(
    private val token: UserDataSource
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authenticatedRequest = request.newBuilder()
            .header("Access-Token", token.getToken())
            .build()
        return chain.proceed(authenticatedRequest)
    }
}