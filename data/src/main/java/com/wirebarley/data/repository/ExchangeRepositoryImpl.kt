package com.wirebarley.data.repository

import com.wirebarley.data.api.ExchangeApi
import com.wirebarley.data.dto.ExchangeErrorResponse
import com.wirebarley.data.dto.ExchangeSuccessResponse
import com.wirebarley.data.mapper.toDomain
import com.wirebarley.data.mapper.toErrorMessage
import com.wirebarley.domain.common.ApiResult
import com.wirebarley.domain.repository.ExchangeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class ExchangeRepositoryImpl @Inject constructor(
    private val exchangeApi: ExchangeApi,
) : ExchangeRepository {
    override fun getExchangeRates() = flow {
        when (val response = exchangeApi.getExchangeRates()) {
            is ExchangeSuccessResponse -> {
                emit(ApiResult.Success(response.toDomain()))
            }

            is ExchangeErrorResponse -> {
                emit(ApiResult.Error(response.toErrorMessage()))
            }
        }
    }.onStart {
        emit(ApiResult.Loading)
    }.catch { e ->
        emit(ApiResult.Error(e.message ?: "잠시 후 다시 시도해주세요."))
    }.flowOn(Dispatchers.IO)
}
