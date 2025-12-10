package com.wirebarley.domain.model

data class ExchangeRate(
    val rates: Map<Currency, Double>,
    val timestamp: Long
)
