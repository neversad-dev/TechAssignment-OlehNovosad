package com.neversad.paymentapp.core.data.di

import com.neversad.paymentapp.core.data.FakeTransactionRepository
import com.neversad.paymentapp.core.domain.TransactionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTransactionRepository(
        fakeTransactionRepository: FakeTransactionRepository
    ): TransactionRepository
} 