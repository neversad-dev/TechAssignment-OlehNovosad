package com.neversad.paymentapp.core.domain.transaction

import com.neversad.paymentapp.core.domain.common.Result
import com.neversad.paymentapp.core.model.Transaction

interface TransactionRepository {

     suspend fun performTransaction(amount: String): Result<Transaction>

     suspend fun getTransaction(transactionId: String): Result<Transaction>
}