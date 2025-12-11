package com.wirebarley.domain.usecase

import java.math.BigDecimal
import javax.inject.Inject

class CalculateExchangeUseCase @Inject constructor() {
    companion object {
        private const val MIN_AMOUNT = 0.0
        private const val MAX_AMOUNT = 10000.0

    }
    operator fun invoke(amount: String, rate: Double): Result<Double> {
        val amount = amount.toDoubleOrNull()

        if (amount == null || amount !in MIN_AMOUNT..MAX_AMOUNT) {
            return Result.failure(
                IllegalArgumentException("송금액이 바르지 않습니다")
            )
        }

        val calculatedAmount = BigDecimal(amount).multiply(BigDecimal.valueOf(rate))

        return Result.success(calculatedAmount.toDouble())
    }
}