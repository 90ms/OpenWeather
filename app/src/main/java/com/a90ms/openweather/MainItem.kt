package com.a90ms.openweather

import com.a90ms.domain.data.dto.ListDto

sealed class MainItem {
    data class Header(val city: String) : MainItem()
    data class Weather(val item: ListDto) : MainItem()
}
