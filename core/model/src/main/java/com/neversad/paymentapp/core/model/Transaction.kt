package com.neversad.paymentapp.core.model

enum class TransactionStatus{
    SUCCESS,
    FAILED
}

data class Transaction (
    val id: String,
    val status: TransactionStatus,
    val purchaseAmount: Double,
    val taxableAmount: Double,
    val taxRate: Double,
    val tipAmount: Double,
    val discountAmount: Double,
    val timestamp: Long,
)

