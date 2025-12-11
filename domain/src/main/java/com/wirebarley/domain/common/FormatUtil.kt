package com.wirebarley.domain.common

import java.math.BigDecimal
import java.math.RoundingMode
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

object FormatUtil {
    @Suppress("ConstantLocale")
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.getDefault())

    fun formatCurrency(value: Double): Double = BigDecimal.valueOf(value)
        .setScale(2, RoundingMode.DOWN).toDouble()

    fun formatAmount(amount: Double): String = BigDecimal.valueOf(amount)
        .setScale(2, RoundingMode.DOWN).toString()

    fun formatTimestamp(timestamp: Long): String = dateFormatter.format(
        Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault())
    )
}