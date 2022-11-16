package com.a90ms.domain.base

data class CommonDto<T : Any>(
    val cod: String,
    val message: Int,
    val count: Int,
    val list: T?
)
