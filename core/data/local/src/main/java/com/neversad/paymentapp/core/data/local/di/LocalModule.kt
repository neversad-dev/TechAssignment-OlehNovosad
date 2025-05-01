package com.neversad.paymentapp.core.data.local.di

import com.neversad.paymentapp.core.data.local.InMemoryTransactionDataSource
import com.neversad.paymentapp.core.domain.transaction.TransactionDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LocalModule {

    @Binds
    @Singleton
    abstract fun bindTransactionDataSource(
        inMemoryTransactionDataSource: InMemoryTransactionDataSource
    ): TransactionDataSource
} 