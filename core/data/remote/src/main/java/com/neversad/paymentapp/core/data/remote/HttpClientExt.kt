package com.neversad.paymentapp.core.data.remote

import com.neversad.paymentapp.core.domain.common.Failure
import com.neversad.paymentapp.core.domain.common.Result
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> executeRequest(
    request: () -> HttpResponse
): Result<T> {

    val response = try {
        request()
    } catch (e: SocketTimeoutException) {
        return Result.Failure(NetworkFailure.Timeout)
    } catch (e: UnresolvedAddressException) {
        return Result.Failure(NetworkFailure.ConnectionLost)
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return Result.Failure(Failure.Unknown)
    }

    return parseResponse(response)
}

suspend inline fun <reified T> parseResponse(
    response: HttpResponse
): Result<T> {
    return when (response.status.value) {
        in 200..299 -> {
            try {
                Result.Success(response.body<T>())
            } catch (e: Exception) {
                Result.Failure(NetworkFailure.InvalidFormat)
            }
        }

        408 -> Result.Failure(NetworkFailure.Timeout)
        in 500..599 -> Result.Failure(NetworkFailure.ServerUnavailable)
        else -> Result.Failure(Failure.Unknown)
    }
}