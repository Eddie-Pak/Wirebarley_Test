package com.wirebarley.data.api

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Named

class ExchangeAuthInterceptor @Inject constructor(
    @Named("apiKey") private val apiKey: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val newUrl = originalUrl.newBuilder()
            .addQueryParameter("access_key", apiKey)
            .addQueryParameter("currencies", "KRW,JPY,PHP")
            .addQueryParameter("source", "USD")
            .addQueryParameter("format", "1")
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}