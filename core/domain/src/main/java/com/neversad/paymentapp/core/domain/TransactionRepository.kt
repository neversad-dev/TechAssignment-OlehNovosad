package com.neversad.paymentapp.core.domain

import com.neversad.paymentapp.core.model.Transaction

interface TransactionRepository {


     fun performTransaction(amount: Double): Transaction
}