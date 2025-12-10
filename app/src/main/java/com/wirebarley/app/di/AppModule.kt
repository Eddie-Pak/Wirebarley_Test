package com.wirebarley.app.di

import com.wirebarley.app.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    @Named("api_key")
    fun provideApiKey(): String {
        return BuildConfig.CURRENCY_LAYER_API_KEY
    }
}