package com.wirebarley.data.mapper

import com.wirebarley.data.dto.ExchangeApiResponse
import com.wirebarley.domain.model.Currency
import org.junit.Assert.assertEquals
import org.junit.Test

class ExchangeRateMapperTest {
    @Test
    fun `정상적인 API 응답을 ExchangeRate로 변환`() {
        val response = ExchangeApiResponse(
            success = true,
            terms = "https://currencylayer.com/terms",
            privacy = "https://currencylayer.com/privacy",
            timestamp = 1765016704L,
            source = "USD",
            quotes = mapOf(
                "USDKRW" to 1473.807789,
                "USDJPY" to 155.360385,
                "USDPHP" to 58.965038
            ),
            error = null
        )

        val result = response.toDomain()

        assertEquals(3, result.rates.size)
        assertEquals(1473.80, result.rates[Currency.KRW])
        assertEquals(155.36, result.rates[Currency.JPY])
        assertEquals(58.96, result.rates[Currency.PHP])
    }
}