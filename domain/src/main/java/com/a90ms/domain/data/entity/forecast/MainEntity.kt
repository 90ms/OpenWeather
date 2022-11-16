package com.a90ms.domain.data.entity.forecast

import com.a90ms.domain.data.dto.MainDto

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
) {
    fun toDto() = MainDto(
        temp = temp,
        feels_like = feels_like,
        temp_min = temp_min,
        temp_max = temp_max,
        pressure = pressure,
        sea_level = sea_level,
        grnd_level = grnd_level,
        humidity = humidity,
        temp_kf = temp_kf
    )
}
