package com.neversad.paymentapp.core.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class TransactionDto(
    val transactionId: String,
    val status: String,
    val amount: Amount,
    val transactionDetails: TransactionDetails
)

@Serializable
internal data class Amount(
    val purchaseAmount: String,
    val currency: String,
    val taxableAmount: String,
    val taxRate: String,
    val tipAmount: String,
    val discountAmount: String
)

@Serializable
internal data class TransactionDetails(
    val timestamp: String
)
