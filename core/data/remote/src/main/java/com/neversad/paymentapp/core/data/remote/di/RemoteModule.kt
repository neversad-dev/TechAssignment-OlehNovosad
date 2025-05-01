package com.neversad.paymentapp.core.data.remote.di

import com.neversad.paymentapp.core.data.remote.FakeTransactionApi
import com.neversad.paymentapp.core.data.remote.HttpClientFactory
import com.neversad.paymentapp.core.domain.transaction.TransactionApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal class RemoteModule {

    @Provides
    @Singleton
    fun provideHttpClientEngine(): HttpClientEngine = CIO.create()

    @Provides
    @Singleton
    fun provideHttpClient(engine: HttpClientEngine): HttpClient = HttpClientFactory.create(engine)

    @Provides
    @Singleton
    fun bindTransactionApi(
        fakeTransactionApi: FakeTransactionApi
    ): TransactionApi = fakeTransactionApi


} 