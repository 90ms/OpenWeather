package com.a90ms.data.di

import com.a90ms.data.api.ApiService
import com.a90ms.data.repository.ApiRepositoryImpl
import com.a90ms.domain.repository.ApiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideApiRepository(
        apiService: ApiService
    ): ApiRepository = ApiRepositoryImpl(apiService)
}
