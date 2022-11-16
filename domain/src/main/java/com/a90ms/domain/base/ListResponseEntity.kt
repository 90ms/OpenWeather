package com.a90ms.domain.base

data class ListResponseEntity<T : DataEntity>(
    override val cod: String,
    override val message: Int,
    override val cnt: Int,
    val list: List<T>
) : ListResponse(
    cod = cod,
    message = message,
    cnt = cnt
)
