package com.wirebarley.data.adapter

import com.squareup.moshi.Moshi
import com.wirebarley.data.api.ExchangeApiResponseAdapter
import com.wirebarley.data.dto.ExchangeApiResponse
import com.wirebarley.data.dto.ExchangeErrorResponse
import com.wirebarley.data.dto.ExchangeSuccessResponse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ExchangeApiResponseAdapterTest {

    private lateinit var moshi: Moshi

    @Before
    fun setup() {
        moshi = Moshi.Builder()
            .add(ExchangeApiResponseAdapter())
            .build()
    }

    @Test
    fun `success가_true인_JSON을_파싱하면_ExchangeSuccessResponse가_반환된다`() {
        val json = """
            {
                "success": true,
                "terms": "terms_url",
                "privacy": "privacy_url",
                "timestamp": 123456789,
                "source": "USD",
                "quotes": {
                    "USDKRW": 1300.5,
                    "USDJPY": 150.0
                }
            }
        """.trimIndent()

        val adapter = moshi.adapter(ExchangeApiResponse::class.java)

        val result = adapter.fromJson(json)

        assertTrue(result is ExchangeSuccessResponse)

        val successResponse = result as ExchangeSuccessResponse
        assertEquals(true, successResponse.success)
        assertEquals(1300.5, successResponse.quotes["USDKRW"])
    }

    @Test
    fun `success가_false인_JSON을_파싱하면_ExchangeErrorResponse가_반환된다`() {
        val json = """
            {
                "success": false,
                "error": {
                    "code": 101,
                    "type": "invalid_access_key",
                    "info": "You have not supplied a valid API Access Key."
                }
            }
        """.trimIndent()

        val adapter = moshi.adapter(ExchangeApiResponse::class.java)

        val result = adapter.fromJson(json)

        assertTrue(result is ExchangeErrorResponse)

        val errorResponse = result as ExchangeErrorResponse
        assertEquals(false, errorResponse.success)
        assertEquals(101, errorResponse.error.code)
        assertEquals("invalid_access_key", errorResponse.error.type)
    }
}