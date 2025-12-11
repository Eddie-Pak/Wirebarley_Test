package com.wirebarley.data.api

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.junit.Assert.assertEquals
import org.junit.Test

class ExchangeAuthInterceptorTest {
    @Test
    fun `Interceptor는_공통_쿼리_파라미터를_자동으로_추가해야_한다`() {
        // Given
        val apiKey = "test_api_key"
        val interceptor = ExchangeAuthInterceptor(apiKey)

        val chain = mockk<Interceptor.Chain>()
        val originalRequest = Request.Builder()
            .url("http://api.test.com/live")
            .build()

        val requestSlot = slot<Request>()

        every { chain.request() } returns originalRequest
        every { chain.proceed(capture(requestSlot)) } returns mockk<Response>()

        interceptor.intercept(chain)

        val interceptedUrl = requestSlot.captured.url

        assertEquals(apiKey, interceptedUrl.queryParameter("access_key"))
        assertEquals("KRW,JPY,PHP", interceptedUrl.queryParameter("currencies"))
        assertEquals("USD", interceptedUrl.queryParameter("source"))
        assertEquals("1", interceptedUrl.queryParameter("format"))
    }
}