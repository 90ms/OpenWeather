package com.a90ms.data.repository

import com.a90ms.data.api.ApiService
import com.a90ms.domain.base.CommonDto
import com.a90ms.domain.data.entity.forecast.ListEntity
import com.a90ms.domain.repository.ApiRepository

class ApiRepositoryImpl(
    private val apiService: ApiService
) : ApiRepository {
    override suspend fun getForecast(lat: Double, lon: Double) =
        apiService.getForecast(lat, lon).run {
            CommonDto(cod, message, this.cnt, list.map(ListEntity::toDto))
        }
}