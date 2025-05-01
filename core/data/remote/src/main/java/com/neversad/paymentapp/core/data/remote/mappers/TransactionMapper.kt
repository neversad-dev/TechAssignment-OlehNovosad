package com.neversad.paymentapp.core.data.remote.mappers

import com.neversad.paymentapp.core.data.remote.dto.TransactionDto
import com.neversad.paymentapp.core.model.Transaction

internal fun TransactionDto.toTransaction(): Transaction {
    return Transaction(
        id = transactionId,
        status = if (status.lowercase() == "success") Transaction.Status.SUCCESS else Transaction.Status.FAILED,
        purchaseAmount = amount.purchaseAmount,
        taxableAmount = amount.taxableAmount,
        taxRate = amount.taxRate,
        tipAmount = amount.tipAmount,
        discountAmount = amount.discountAmount,
        timestamp = transactionDetails.timestamp
    )
}