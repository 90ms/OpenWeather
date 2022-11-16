package com.a90ms.domain.data.entity.forecast

data class WeatherEntity(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String
)
