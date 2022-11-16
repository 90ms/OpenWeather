package com.a90ms.domain.base

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(
        val httpCode: Int? = 0,
        val cod: String? = "",
        val message: Int? = 0,
        val messageString: String? = ""
    ) : Result<Nothing>()

    data class Exception(val exception: kotlin.Exception) : Result<Nothing>()
}
