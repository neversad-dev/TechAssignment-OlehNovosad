package com.neversad.paymentapp.core.data.remote

import com.neversad.paymentapp.core.data.remote.dto.TransactionDto
import com.neversad.paymentapp.core.data.remote.mappers.toTransaction
import com.neversad.paymentapp.core.domain.TransactionApi
import com.neversad.paymentapp.core.model.Transaction
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import java.util.UUID
import javax.inject.Inject
import kotlin.random.Random

private const val FAKE_TRANSACTION_URL = "https://jason-koala.wallee.workers.dev/"

internal class FakeTransactionApi @Inject constructor(
    private val client: HttpClient
) : TransactionApi {

    override suspend fun performTransaction(amount: Double): Transaction {

        val result: TransactionDto = client.get(FAKE_TRANSACTION_URL).body()


        return result.toTransaction()
    }
}