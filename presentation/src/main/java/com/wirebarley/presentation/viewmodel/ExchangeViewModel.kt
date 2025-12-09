package com.wirebarley.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.wirebarley.domain.model.Currency
import com.wirebarley.domain.model.ExchangeData
import com.wirebarley.domain.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor() : ViewModel() {
    // mockData
    private val mockExchangeRates = mapOf(
        Currency.KRW to 1350.232,
        Currency.JPY to 150.345,
        Currency.PHP to 58.123
    )

    private val _uiState = MutableStateFlow(
        UiState(
            data = ExchangeData(
                exchangeRates = mockExchangeRates,
                currentRate = mockExchangeRates[Currency.KRW] ?: 0.0,
                formattedRate = formatAmount(mockExchangeRates[Currency.KRW] ?: 0.0),
                queryTime = getCurrentTime()
            )
        )
    )
    val uiState get() = _uiState.asStateFlow()

    fun selectCurrency(currency: Currency) {
        val newRate = _uiState.value.data.exchangeRates[currency] ?: 0.0

        _uiState.update { state ->
            state.copy(
                data = state.data.copy(
                    selectedCurrency = currency,
                    currentRate = newRate,
                    formattedRate = formatAmount(newRate),
                    sendAmount = "",
                    receiveAmount = ""
                ),
                errorMessage = null
            )
        }
    }

    fun updateSendAmount(amount: String) {
        val sanitizedAmount = if (amount.length > 1 && amount.startsWith("0") && !amount.startsWith("0.")) {
            amount.trimStart('0')
        } else {
            amount
        }

        _uiState.update { state ->
            state.copy(
                data = state.data.copy(sendAmount = sanitizedAmount),
                errorMessage = null
            )
        }

        if (amount.isEmpty()) {
            _uiState.update { state ->
                state.copy(
                    data = state.data.copy(receiveAmount = "")
                )
            }
            return
        }

        calculateExchange(sanitizedAmount)
    }

    private fun calculateExchange(amount: String) {
        val amountDouble = amount.toDoubleOrNull()

        // 유효성 검사
        val isValid = amountDouble != null && amountDouble in 0.0..10000.0

        if (!isValid) {
            _uiState.update { state ->
                state.copy(
                    data = state.data.copy(receiveAmount = ""),
                    errorMessage = "송금액이 바르지 않습니다"
                )
            }
            return
        }

        // 계산 로직
        val result = amountDouble * _uiState.value.data.currentRate

        _uiState.update { state ->
            state.copy(
                data = state.data.copy(receiveAmount = formatAmount(result)),
                errorMessage = null
            )
        }
    }

    private fun formatAmount(amount: Double): String = DecimalFormat("#,##0.00").format(amount)


    private fun getCurrentTime(): String = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())
}