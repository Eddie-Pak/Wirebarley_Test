package com.wirebarley.data.di

import com.wirebarley.data.repository.ExchangeRepositoryImpl
import com.wirebarley.domain.repository.ExchangeRepository
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
    abstract fun bindExchangeRepository(exchangeRepositoryImpl: ExchangeRepositoryImpl): ExchangeRepository

}