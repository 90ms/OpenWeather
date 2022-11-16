package com.a90ms.domain.data.entity.forecast

import com.a90ms.domain.data.dto.SysDto

data class SysEntity(
    val pod: String
) {
    fun toDto() = SysDto(
        pod = pod
    )
}
