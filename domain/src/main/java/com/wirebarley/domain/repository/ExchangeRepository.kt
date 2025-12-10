package com.wirebarley.domain.repository

import com.wirebarley.domain.common.ApiResult
import com.wirebarley.domain.model.ExchangeRate
import kotlinx.coroutines.flow.Flow

interface ExchangeRepository {
    fun getExchangeRates(): Flow<ApiResult<ExchangeRate>>
}