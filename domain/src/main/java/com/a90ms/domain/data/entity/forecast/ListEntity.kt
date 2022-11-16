package com.a90ms.domain.data.entity.forecast

import com.a90ms.common.convertTimestampToDate
import com.a90ms.domain.base.DataEntity
import com.a90ms.domain.data.dto.ListDto

data class ListEntity(
    val dt: Long,
    val main: MainEntity,
    val weather: List<WeatherEntity>,
    val clouds: CloudsEntity,
    val wind: WindEntity,
    val visibility: Long,
    val pop: Double,
    val sys: SysEntity,
    val dt_txt: String
) : DataEntity {
    fun toDto() = ListDto(
        dt = dt,
        dateToString = (dt * 1000L).convertTimestampToDate(),
        main = main.toDto(),
        weather = weather.map(WeatherEntity::toDto),
        clouds = clouds.toDto(),
        wind = wind.toDto(),
        visibility = visibility,
        pop = pop,
        sys = sys.toDto(),
        dt_txt = dt_txt,
        shortDate = dt_txt.substring(0, 10)
    )
}
