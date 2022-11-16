package com.a90ms.data.api

import com.a90ms.domain.base.ListResponseEntity
import com.a90ms.domain.data.entity.forecast.ListEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("data/2.5/forecast")
    suspend fun getForecast(
        @Query(QUERY_LAT) lat: Double,
        @Query(QUERY_LON) lon: Double,
        @Query(QUERY_APP_ID) appId: String? = "42907c562ec66f88eae7f9cca5914cca"
    ): ListResponseEntity<ListEntity>

    companion object {
        const val QUERY_LAT = "lat"
        const val QUERY_LON = "lon"
        const val QUERY_APP_ID = "appid"
    }
}