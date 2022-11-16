package com.a90ms.domain.data.dto

import com.a90ms.domain.base.DataEntity

data class ListDto(
    val dt: Long,
    val main: MainDto,
    val weather: List<WeatherDto>,
    val clouds: CloudsDto,
    val wind: WindDto,
    val visibility: Long,
    val pop: Double,
    val sys: SysDto,
    val dt_txt: String,
    val shortDate: String
) : DataEntity
