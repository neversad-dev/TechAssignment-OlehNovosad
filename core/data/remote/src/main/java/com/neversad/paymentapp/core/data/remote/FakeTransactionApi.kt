package com.neversad.paymentapp.core.data.remote

import com.neversad.paymentapp.core.data.remote.dto.TransactionDto
import com.neversad.paymentapp.core.data.remote.mappers.toTransaction
import com.neversad.paymentapp.core.domain.common.Result
import com.neversad.paymentapp.core.domain.transaction.TransactionApi
import com.neversad.paymentapp.core.model.Transaction
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import javax.inject.Inject

private const val FAKE_TRANSACTION_URL = "https://jason-koala.wallee.workers.dev/"

internal class FakeTransactionApi @Inject constructor(
    private val client: HttpClient
) : TransactionApi {

    override suspend fun performTransaction(amount: Double): Result<Transaction> {
        return executeRequest {
            client.get(FAKE_TRANSACTION_URL)
        }
//            .map { it.toTransaction() }

    }
}