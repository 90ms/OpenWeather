package com.a90ms.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query(QUERY_CITY) city: String? = "Seoul",
        @Query(QUERY_APP_ID) appId: String? = "42907c562ec66f88eae7f9cca5914cca"
    )

    companion object {
        const val QUERY_CITY = "q"
        const val QUERY_APP_ID = "appid"
    }
}