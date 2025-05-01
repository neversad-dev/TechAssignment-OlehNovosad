package com.neversad.paymentapp.core.data.local

import com.neversad.paymentapp.core.domain.TransactionDataSource
import com.neversad.paymentapp.core.model.Transaction
import jakarta.inject.Inject

 class InMemoryTransactionDataSource @Inject constructor() : TransactionDataSource {

    private val transactions = mutableListOf<Transaction>()

    override fun saveTransaction(transaction: Transaction) {
        transactions.add(transaction)
    }

    override fun getTransactionById(id: String): Transaction? {
        return transactions.find { it.id == id }
    }
}