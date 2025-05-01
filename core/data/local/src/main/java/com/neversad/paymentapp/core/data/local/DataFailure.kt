package com.neversad.paymentapp.core.data.local

import com.neversad.paymentapp.core.domain.common.Failure

sealed class DataFailure(override val message: String): Failure(message) {
    data object NotFound : DataFailure("Data not found")
}