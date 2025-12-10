package com.wirebarley.domain.usecase

import javax.inject.Inject

class CalculateExchangeUseCase @Inject constructor() {
    operator fun invoke(amount: String, rate: Double): Result<Double> {
        val amount = amount.toDoubleOrNull()

        if (amount == null || amount !in 0.0..10000.0) {
            return Result.failure(
                IllegalArgumentException("송금액이 바르지 않습니다")
            )
        }

        return Result.success(amount * rate)
    }
}