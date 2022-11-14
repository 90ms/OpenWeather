package com.a90ms.data.repository

import com.a90ms.data.api.ApiService
import com.a90ms.domain.repository.ApiRepository

class ApiRepositoryImpl(
    private val apiService: ApiService
) : ApiRepository {
    override suspend fun getWeather(city: String) {
        apiService.getWeather(city)
    }
}