package com.a90ms.domain.data.entity.forecast

import com.a90ms.domain.base.DataEntity

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
): DataEntity
