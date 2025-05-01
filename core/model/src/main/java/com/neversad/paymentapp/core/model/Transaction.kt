package com.neversad.paymentapp.core.model


data class Transaction(
    val id: String,
    val status: Status,
    val purchaseAmount: String,
    val taxableAmount: String,
    val taxRate: String,
    val tipAmount: String,
    val discountAmount: String,
    val timestamp: String,
) {
    enum class Status {
        SUCCESS,
        FAILED
    }
}

