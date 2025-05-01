package com.neversad.paymentapp.core.domain.internal.di

import com.neversad.paymentapp.core.domain.internal.TransactionRepositoryImpl
import com.neversad.paymentapp.core.domain.TransactionRepository
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