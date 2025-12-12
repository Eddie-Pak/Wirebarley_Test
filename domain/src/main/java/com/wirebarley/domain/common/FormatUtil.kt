package com.wirebarley.domain.common

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

object FormatUtil {
    @Suppress("ConstantLocale")
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.getDefault())

    fun formatCurrency(value: Double): Double = BigDecimal.valueOf(value)
        .setScale(2, RoundingMode.DOWN).toDouble()

    fun formatAmount(amount: Double): String {
        val truncated = BigDecimal.valueOf(amount).setScale(2, RoundingMode.DOWN)
        return DecimalFormat("#,##0.00").format(truncated)
    }

    fun formatTimestamp(timestamp: Long): String = dateFormatter.format(
        Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault())
    )
}