package com.wirebarley.data.mapper

import com.wirebarley.data.dto.ErrorDetail
import com.wirebarley.data.dto.ExchangeErrorResponse
import com.wirebarley.data.dto.ExchangeSuccessResponse
import com.wirebarley.domain.model.Currency
import org.junit.Assert.assertEquals
import org.junit.Test

class ExchangeRateMapperTest {
    @Test
    fun `ExchangeSuccessResponse를_도메인_모델(ExchangeRate)로_변환한다`() {
        val response = ExchangeSuccessResponse(
            success = true,
            terms = "https://currencylayer.com/terms",
            privacy = "https://currencylayer.com/privacy",
            timestamp = 1765016704L,
            source = "USD",
            quotes = mapOf(
                "USDKRW" to 1473.807789,
                "USDJPY" to 155.360385,
                "USDPHP" to 58.965038
            )
        )

        val result = response.toDomain()

        assertEquals(1765016704L, result.timestamp)
        assertEquals(3, result.rates.size)

        assertEquals(1473.80, result.rates[Currency.KRW])
        assertEquals(155.36, result.rates[Currency.JPY])
        assertEquals(58.96, result.rates[Currency.PHP])
    }

    @Test
    fun `ExchangeErrorResponse에서_에러_메시지를_정상적으로_추출한다`() {
        val expectedMessage = "You have not supplied a valid API Access Key."
        val response = ExchangeErrorResponse(
            success = false,
            error = ErrorDetail(
                code = 101,
                type = "invalid_access_key",
                info = expectedMessage
            )
        )

        val result = response.toErrorMessage()

        assertEquals(expectedMessage, result)
    }
}