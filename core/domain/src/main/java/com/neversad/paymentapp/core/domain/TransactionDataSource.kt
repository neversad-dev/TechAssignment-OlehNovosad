package com.neversad.paymentapp.core.domain

import com.neversad.paymentapp.core.model.Transaction

interface TransactionDataSource {

    fun saveTransaction(transaction: Transaction)

    fun getTransactionById(id: String): Transaction?
}