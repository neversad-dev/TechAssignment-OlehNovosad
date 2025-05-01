package com.neversad.paymentapp.core.domain.transaction.implementation

import com.neversad.paymentapp.core.domain.common.Result
import com.neversad.paymentapp.core.domain.transaction.TransactionApi
import com.neversad.paymentapp.core.domain.transaction.TransactionDataSource
import com.neversad.paymentapp.core.domain.transaction.TransactionRepository
import com.neversad.paymentapp.core.model.Transaction
import javax.inject.Inject

internal class TransactionRepositoryImpl @Inject constructor(
    private val transactionApi: TransactionApi,
    private val transactionDataSource: TransactionDataSource
) : TransactionRepository {

    override suspend fun performTransaction(amount: Double): Result<Transaction> {
        return transactionApi
            .performTransaction(amount)
            .flatMap {
                transactionDataSource.saveTransaction(it)
            }
    }
}