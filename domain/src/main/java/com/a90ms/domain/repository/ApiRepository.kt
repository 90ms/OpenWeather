package com.a90ms.domain.repository

interface ApiRepository {

    suspend fun getWeather(city: String)
}