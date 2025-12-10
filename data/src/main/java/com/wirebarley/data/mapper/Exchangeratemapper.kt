package com.wirebarley.data.mapper

import com.wirebarley.data.dto.ExchangeSuccessResponse
import com.wirebarley.domain.model.Currency
import com.wirebarley.domain.model.ExchangeRate
import java.math.BigDecimal
import java.math.RoundingMode

fun ExchangeSuccessResponse.toDomain(): ExchangeRate {
    val rates = quotes.mapNotNull { (key, value) ->
        // "USDKRW" → "KRW" 추출
        val currencyCode = key.removePrefix("USD")

        val currency = Currency.entries.find { it.code == currencyCode }

        currency?.let { it to truncateToTwoDecimals(value) }
    }.toMap()

    return ExchangeRate(
        rates = rates,
        timestamp = timestamp
    )
}


private fun truncateToTwoDecimals(value: Double): Double {
    return BigDecimal.valueOf(value)
        .setScale(2, RoundingMode.DOWN)
        .toDouble()
}