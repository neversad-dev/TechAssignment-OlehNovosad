package com.neversad.paymentapp.core.data.remote

import com.neversad.paymentapp.core.domain.TransactionApi
import com.neversad.paymentapp.core.model.Transaction
import com.neversad.paymentapp.core.model.TransactionStatus
import java.util.UUID
import javax.inject.Inject
import kotlin.random.Random

internal class FakeTransactionApi @Inject constructor() : TransactionApi {

    override fun performTransaction(amount: Double): Transaction {
        // Simulate some processing time
//        Thread.sleep(500)

        // Generate a random success rate (90% success)
        val isSuccess = Random.nextDouble() < 0.9

        return Transaction(
            id = UUID.randomUUID().toString(),
            status = if (isSuccess) TransactionStatus.SUCCESS else TransactionStatus.FAILED,
            purchaseAmount = amount,
            // Calculate taxable amount (excluding tips and discounts)
            taxableAmount = amount * 0.9, // Assume 90% of amount is taxable
            taxRate = 0.1, // 10% tax rate
            tipAmount = amount * 0.1, // Default 10% tip
            discountAmount = if (amount > 100) amount * 0.05 else 0.0, // 5% discount for purchases over $100
            timestamp = System.currentTimeMillis()
        )
    }
}