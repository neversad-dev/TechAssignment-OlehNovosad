package com.neversad.paymentapp.core.data.local

import com.neversad.paymentapp.core.domain.common.Failure
import com.neversad.paymentapp.core.domain.common.Result
import com.neversad.paymentapp.core.domain.transaction.TransactionDataSource
import com.neversad.paymentapp.core.model.Transaction
import jakarta.inject.Inject



internal class InMemoryTransactionDataSource @Inject constructor() : TransactionDataSource {

    private val transactions = mutableListOf<Transaction>()

    override suspend fun saveTransaction(transaction: Transaction): Result<Transaction> {
        transactions.add(transaction)
        return Result.Success(transaction)
    }

    override suspend fun getTransactionById(id: String): Result<Transaction> {
        val result = transactions.find { it.id == id }
        return if (result != null) {
            Result.Success(result)
        } else {
            Result.Failure(DataFailure.NotFound)
        }
    }
}