package com.neversad.paymentapp.core.domain.common

open class Failure  {
    sealed class Network : Failure() {
        data object ConnectionLost : Network()
        data object Timeout : Network()
        data object ServerUnavailable : Network()
    }

    open class Data : Failure() {
        data object InvalidFormat : Data()
        data object UnexpectedResponse : Data()
    }
    
    data object Unknown : Failure()
}