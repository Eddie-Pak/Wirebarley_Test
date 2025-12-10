package com.wirebarley.domain.model

data class ExchangeData(
    val selectedCurrency: Currency = Currency.KRW,
    val exchangeRates: Map<Currency, Double> = emptyMap(),
    val currentRate: Double = 0.0,
    val sendAmount: String = "",
    val receiveAmount: String = "",
    val timestamp: Long = 0L,
    val formattedDate: String = ""
)