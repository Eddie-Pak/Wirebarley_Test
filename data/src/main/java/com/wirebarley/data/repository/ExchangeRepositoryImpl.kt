package com.wirebarley.data.repository

import com.wirebarley.data.api.ExchangeApi
import com.wirebarley.data.mapper.toDomain
import com.wirebarley.domain.common.ApiResult
import com.wirebarley.domain.repository.ExchangeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ExchangeRepositoryImpl @Inject constructor(
    private val exchangeApi: ExchangeApi,
) : ExchangeRepository {
    override fun getExchangeRates() = flow {
        emit(ApiResult.Loading)

        val response = exchangeApi.getExchangeRates()

        if (response.success) {
            emit(ApiResult.Success(response.toDomain()))
        } else {
            emit(ApiResult.Error(response.error?.info ?: "잠시 후 다시 시도해주세요."))
        }
    }.catch { e ->
        emit(ApiResult.Error(e.message ?: "잠시 후 다시 시도해주세요."))
    }.flowOn(Dispatchers.IO)
}
