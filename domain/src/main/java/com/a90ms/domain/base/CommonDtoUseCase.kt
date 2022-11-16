package com.a90ms.domain.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class CommonDtoUseCase<in P, R : Any>(
    private val coroutineDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(parameters: P): Result<R> {
        return try {
            withContext(coroutineDispatcher) {
                execute(parameters).let {
                    if (it.cod == "200" && it.list != null) {
                        Result.Success(it.list)
                    } else {
                        Result.Error(cod = it.cod, message = it.message)
                    }
                }
            }
        } catch (e: HttpException) {
            Result.Error(httpCode = e.code(), messageString = e.message())
        } catch (e: Exception) {
            Result.Exception(e)
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: P): CommonDto<R>
}
