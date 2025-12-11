package com.wirebarley.data.repository

import app.cash.turbine.test
import com.wirebarley.data.api.ExchangeApi
import com.wirebarley.data.dto.ErrorDetail
import com.wirebarley.data.dto.ExchangeApiResponse
import com.wirebarley.domain.common.ApiResult
import com.wirebarley.domain.model.Currency
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExchangeRepositoryImplTest {
    private val exchangeApi = mockk<ExchangeApi>()

    private val repository = ExchangeRepositoryImpl(exchangeApi)

    @Test
    fun `API 호출 성공 시 Loading과 Success 순서대로 emit`() = runTest {

        val response = ExchangeApiResponse(
            success = true,
            quotes = mapOf(
                "USDKRW" to 1350.0,
                "USDJPY" to 155.0,
                "USDPHP" to 58.0
            ),
            error = null
        )
        coEvery { exchangeApi.getExchangeRates() } returns response

        val results = repository.getExchangeRates().toList()

        assertEquals(2, results.size)
        assertTrue(results[0] is ApiResult.Loading)
        assertTrue(results[1] is ApiResult.Success)

        val successResult = results[1] as ApiResult.Success

        assertEquals(3, successResult.data.rates.size)
        assertEquals(1350.0, successResult.data.rates[Currency.KRW])
    }

    @Test
    fun `API 응답 success가 false일 때 Error emit`() = runTest {
        val errorMessage = "Invalid API Key"
        val response = ExchangeApiResponse(
            success = false,
            error = ErrorDetail(
                code = 101,
                type = "invalid_access_key",
                info = errorMessage
            )
        )
        coEvery { exchangeApi.getExchangeRates() } returns response

        val results = repository.getExchangeRates().toList()

        assertEquals(2, results.size)
        assertTrue(results[0] is ApiResult.Loading)
        assertTrue(results[1] is ApiResult.Error)

        val errorResult = results[1] as ApiResult.Error

        assertEquals(errorMessage, errorResult.message)
    }
}