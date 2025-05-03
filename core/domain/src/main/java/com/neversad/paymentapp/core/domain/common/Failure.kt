package com.neversad.paymentapp.core.domain.common

open class Failure(open val message: String) {

    data object Unknown : Failure("Unknown error")
}