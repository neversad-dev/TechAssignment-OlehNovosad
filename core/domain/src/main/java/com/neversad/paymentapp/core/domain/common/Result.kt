package com.neversad.paymentapp.core.domain.common

sealed class Result<out T> {
    data class Success<T>(
        val data: T,
    ) : Result<T>()

    data class Failure(
        val error: com.neversad.paymentapp.core.domain.common.Failure,
    ) : Result<Nothing>()

    inline fun <R> map(transform: (T) -> R): Result<R> =
        when (this) {
            is Success -> Success(transform(data))
            is Failure -> Failure(error)
        }

    inline fun <R> flatMap(transform: (T) -> Result<R>): Result<R> =
        when (this) {
            is Success -> transform(data)
            is Failure -> Failure(error)
        }

    inline fun onSuccess(action: (T) -> Unit): Result<T> {
        if (this is Success) action(data)
        return this
    }

    inline fun onFailure(action: (com.neversad.paymentapp.core.domain.common.Failure) -> Unit): Result<T> {
        if (this is Failure) action(error)
        return this
    }
}
