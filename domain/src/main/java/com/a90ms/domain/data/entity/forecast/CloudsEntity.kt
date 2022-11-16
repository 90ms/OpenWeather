package com.a90ms.domain.data.entity.forecast

import com.a90ms.domain.data.dto.CloudsDto

data class CloudsEntity(
    val all: Long
) {
    fun toDto() = CloudsDto(
        all = all
    )
}
