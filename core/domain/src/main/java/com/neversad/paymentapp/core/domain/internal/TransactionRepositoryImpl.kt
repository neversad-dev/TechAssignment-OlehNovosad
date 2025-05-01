package com.neversad.paymentapp.core.domain.internal

import com.neversad.paymentapp.core.domain.TransactionApi
import com.neversad.paymentapp.core.domain.TransactionDataSource
import com.neversad.paymentapp.core.domain.TransactionRepository
import com.neversad.paymentapp.core.model.Transaction
import java.util.UUID
import javax.inject.Inject
import kotlin.random.Random

internal class TransactionRepositoryImpl @Inject constructor(
    private val transactionApi: TransactionApi,
    private val transactionDataSource: TransactionDataSource
) : TransactionRepository {

    override suspend fun performTransaction(amount: Double): Transaction {
        return transactionApi.performTransaction(amount)

            .also { transaction ->
                transactionDataSource.saveTransaction(transaction)
            }
    }
}