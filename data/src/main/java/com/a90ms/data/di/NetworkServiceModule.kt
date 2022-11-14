package com.a90ms.data.di

import com.a90ms.data.api.ApiService
import com.a90ms.data.api.Const.HOST_NAME_CUR
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkServiceModule {

    @Singleton
    @Provides
    fun provideApiService(
        @Named(HOST_NAME_CUR)
        retrofitBuilder: Retrofit.Builder
    ): ApiService = retrofitBuilder.build().create(ApiService::class.java)
}