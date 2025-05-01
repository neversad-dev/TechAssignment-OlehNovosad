package com.neversad.paymentapp.core.domain.transaction

import com.neversad.paymentapp.core.domain.common.Result
import com.neversad.paymentapp.core.model.Transaction

interface TransactionDataSource {

    suspend fun saveTransaction(transaction: Transaction): Result<Transaction>

    suspend fun getTransactionById(id: String): Result<Transaction>
}