package com.wirebarley.data.api

import com.wirebarley.data.dto.ExchangeApiResponse
import retrofit2.http.GET

interface ExchangeApi {
    @GET("live")
    suspend fun getExchangeRates(): ExchangeApiResponse
}