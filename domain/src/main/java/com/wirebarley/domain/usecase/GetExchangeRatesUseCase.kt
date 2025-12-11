package com.wirebarley.domain.usecase

import com.wirebarley.domain.common.ApiResult
import com.wirebarley.domain.model.ExchangeRate
import com.wirebarley.domain.repository.ExchangeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExchangeRatesUseCase @Inject constructor(
    private val exchangeRepository: ExchangeRepository
) {
    operator fun invoke(): Flow<ApiResult<ExchangeRate>> = exchangeRepository.getExchangeRates()
}