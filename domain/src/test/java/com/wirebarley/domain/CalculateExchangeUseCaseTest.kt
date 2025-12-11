package com.wirebarley.domain

import com.wirebarley.domain.usecase.CalculateExchangeUseCase
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CalculateExchangeUseCaseTest {
    private val calculateExchangeUseCase = CalculateExchangeUseCase()

    @Test
    fun `10을_58_91_환율로_계산하면_589_1이_나와야_한다`() {
        // Given
        val amount = "10"
        val rate = 58.91

        val result = calculateExchangeUseCase(amount, rate)

        assertTrue(result.isSuccess)
        assertEquals(589.1, result.getOrNull()!!, 0.0)
    }

    @Test
    fun `숫자가_아닌_문자열을_입력하면_실패해야_한다`() {
        // Given
        val amount = "abc"
        val rate = 1000.0

        // When
        val result = calculateExchangeUseCase(amount, rate)

        // Then
        assertTrue(result.isFailure)
        assertEquals("송금액이 바르지 않습니다", result.exceptionOrNull()?.message)
    }

    @Test
    fun `송금액이_0보다_작으면_실패해야_한다`() {
        // Given
        val amount = "-1"
        val rate = 1000.0

        // When
        val result = calculateExchangeUseCase(amount, rate)

        // Then
        assertTrue(result.isFailure)
        assertEquals("송금액이 바르지 않습니다", result.exceptionOrNull()?.message)
    }

    @Test
    fun `송금액이_10000보다_크면_실패해야_한다`() {
        // Given
        val amount = "10001"
        val rate = 1000.0

        // When
        val result = calculateExchangeUseCase(amount, rate)

        // Then
        assertTrue(result.isFailure)
        assertEquals("송금액이 바르지 않습니다", result.exceptionOrNull()?.message)
    }
}