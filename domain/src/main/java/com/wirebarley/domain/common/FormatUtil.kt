package com.wirebarley.domain.common

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object FormatUtil {
    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

    fun formatCurrency(value: Double): Double = BigDecimal.valueOf(value)
        .setScale(2, RoundingMode.DOWN).toDouble()

    fun formatAmount(amount: Double): String = BigDecimal.valueOf(amount)
        .setScale(2, RoundingMode.DOWN).toString()

    fun formatTimestamp(timestamp: Long): String {
        val date = Date(timestamp * 1000)
        return dateFormatter.format(date)
    }
}