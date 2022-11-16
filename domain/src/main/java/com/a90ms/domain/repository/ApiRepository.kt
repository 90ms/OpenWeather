package com.a90ms.domain.repository

import com.a90ms.domain.base.CommonDto
import com.a90ms.domain.data.dto.ListDto

interface ApiRepository {

    suspend fun getForecast(lat: Double, lon: Double): CommonDto<List<ListDto>>
}
