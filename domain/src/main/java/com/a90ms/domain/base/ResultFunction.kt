package com.a90ms.domain.base

inline fun <reified T> Result<T>.onSuccess(action: (data: T) -> Unit): Result<T> {
    if (this is Result.Success) {
        action(data)
    }

    return this
}

inline fun <reified T> Result<T>.onError(
    action: (code: String, message: String) -> Unit
): Result<T> {
    if (this is Result.Error && httpCode in 200..299) {
        action(cod.toString(), message?.toString() ?: "")
    }

    return this
}

inline fun <reified T> Result<T>.onException(
    action: (exception: Exception) -> Unit
): Result<T> {
    if (this is Result.Exception) {
        action(exception)
    }

    return this
}
