package com.neversad.paymentapp.core.domain.di

import com.neversad.paymentapp.core.domain.transaction.TransactionRepository
import com.neversad.paymentapp.core.domain.transaction.implementation.TransactionRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTransactionRepository(
        fakeTransactionRepository: TransactionRepositoryImpl
    ): TransactionRepository
} 