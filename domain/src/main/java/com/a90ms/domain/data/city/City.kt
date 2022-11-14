package com.a90ms.domain.data.city

data class City(
    val id: Long,
    val name: String,
    val state: String,
    val country: String,
    val coord: Coord
)
