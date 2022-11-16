package com.a90ms.domain.data.entity.forecast

import com.a90ms.domain.data.dto.WeatherDto

data class WeatherEntity(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String
) {
    fun toDto() = WeatherDto(
        id = id,
        main = main,
        description = description,
        icon = icon,
        iconUrl = "http://openweathermap.org/img/wn/${icon}@2x.png"
    )
}
