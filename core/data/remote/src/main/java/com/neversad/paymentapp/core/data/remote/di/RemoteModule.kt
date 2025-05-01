package com.neversad.paymentapp.core.data.remote.di

import com.neversad.paymentapp.core.data.remote.FakeTransactionApi
import com.neversad.paymentapp.core.domain.TransactionApi
import com.neversad.paymentapp.core.domain.TransactionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RemoteModule {

    @Binds
    @Singleton
    abstract fun bindTransactionApi(
        fakeTransactionApi: FakeTransactionApi
    ): TransactionApi
} 