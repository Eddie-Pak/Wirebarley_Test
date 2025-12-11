package com.wirebarley.data.api

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Named

class ExchangeAuthInterceptor @Inject constructor(
    @Named("apiKey") private val apiKey: String
) : Interceptor {
    companion object {
        private const val QUERY_ACCESS_KEY = "access_key"
        private const val QUERY_CURRENCIES = "currencies"
        private const val QUERY_SOURCE = "source"
        private const val QUERY_FORMAT = "format"

        private const val DEFAULT_CURRENCIES = "KRW,JPY,PHP"
        private const val DEFAULT_SOURCE = "USD"
        private const val DEFAULT_FORMAT = "1"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val newUrl = originalUrl.newBuilder()
            .addQueryParameter(QUERY_ACCESS_KEY, apiKey)
            .addQueryParameter(QUERY_CURRENCIES, DEFAULT_CURRENCIES)
            .addQueryParameter(QUERY_SOURCE, DEFAULT_SOURCE)
            .addQueryParameter(QUERY_FORMAT, DEFAULT_FORMAT)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}