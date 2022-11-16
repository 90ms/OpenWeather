package com.a90ms.domain.repository

import com.a90ms.domain.data.entity.forecast.ListEntity

interface ApiRepository {

    suspend fun getForecast(lat: Double, lon: Double): List<ListEntity>
}