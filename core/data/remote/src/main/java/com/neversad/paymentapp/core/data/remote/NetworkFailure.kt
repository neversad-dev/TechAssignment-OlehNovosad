package com.neversad.paymentapp.core.data.remote

import com.neversad.paymentapp.core.domain.common.Failure

sealed class NetworkFailure(override val message: String) : Failure(message) {
    data object ConnectionLost : NetworkFailure("Connection lost")
    data object Timeout : NetworkFailure("Connection timeout")
    data object ServerUnavailable : NetworkFailure("Server unavailable")
    data object InvalidFormat : NetworkFailure("Invalid format")
}