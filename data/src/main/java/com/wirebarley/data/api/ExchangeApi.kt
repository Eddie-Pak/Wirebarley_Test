package com.wirebarley.data.api

import com.wirebarley.data.dto.ExchangeApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeApi {
    @GET("live")
    suspend fun getExchangeRates(
        @Query("access_key") accessKey: String,
        @Query("currencies") currencies: String = "KRW,JPY,PHP",
        @Query("source") source: String = "USD",
        @Query("format") format: Int = 1
    ): ExchangeApiResponse
}