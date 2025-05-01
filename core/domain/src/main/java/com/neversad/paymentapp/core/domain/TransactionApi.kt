package com.neversad.paymentapp.core.domain

import com.neversad.paymentapp.core.model.Transaction

interface TransactionApi {

    suspend fun performTransaction(amount: Double): Transaction
}