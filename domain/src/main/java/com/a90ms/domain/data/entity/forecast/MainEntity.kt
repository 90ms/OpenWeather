package com.a90ms.domain.data.entity.forecast

data class MainEntity(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Long,
    val sea_level: Long,
    val grnd_level: Long,
    val humidity: Long,
    val temp_kf: Double
)
