package com.a90ms.domain.data.entity.forecast

import com.a90ms.domain.data.dto.WindDto

data class WindEntity(
    val speed: Double,
    val deg: Long,
    val gust: Double
) {
    fun toDto() = WindDto(
        speed = speed, deg = deg, gust = gust
    )
}
