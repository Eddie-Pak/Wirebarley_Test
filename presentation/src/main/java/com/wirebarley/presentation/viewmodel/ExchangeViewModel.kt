package com.wirebarley.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wirebarley.domain.common.ApiResult
import com.wirebarley.domain.common.FormatUtil
import com.wirebarley.domain.model.Currency
import com.wirebarley.domain.model.ExchangeData
import com.wirebarley.domain.usecase.CalculateExchangeUseCase
import com.wirebarley.domain.usecase.GetExchangeRatesUseCase
import com.wirebarley.presentation.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val getExchangeRatesUseCase: GetExchangeRatesUseCase,
    private val calculateExchangeUseCase: CalculateExchangeUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState(isLoading = true, data = ExchangeData()))

    val uiState get() = _uiState.asStateFlow()

    init {
        fetchExchangeRates()
    }

    private fun fetchExchangeRates() {
        viewModelScope.launch {
            getExchangeRatesUseCase().collectLatest { result ->
                _uiState.update { state ->
                    when (result) {
                        is ApiResult.Loading -> state.copy(isLoading = true)

                        is ApiResult.Success -> {
                            val rates = result.data.rates
                            val targetCurrency = state.data.selectedCurrency
                            val currentRate = rates[targetCurrency] ?: 0.0

                            state.copy(
                                isLoading = false,
                                data = state.data.copy(
                                    exchangeRates = rates,
                                    currentRate = currentRate,
                                    selectedCurrency = targetCurrency,
                                    timestamp = result.data.timestamp,
                                    formattedDate = FormatUtil.formatTimestamp(result.data.timestamp)
                                ),
                                errorMessage = null
                            )
                        }

                        is ApiResult.Error -> state.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }
                }
            }
        }
    }

    fun selectCurrency(currency: Currency) {
        _uiState.update { state ->
            val newRate = state.data.exchangeRates[currency] ?: 0.0

            state.copy(
                data = state.data.copy(
                    selectedCurrency = currency,
                    currentRate = newRate,
                    sendAmount = "",
                    receiveAmount = ""
                ),
                errorMessage = null
            )
        }
    }

    fun updateSendAmount(amount: String) {
        val sanitizedAmount = sanitizeInput(amount)

        _uiState.update { state ->
            if (sanitizedAmount.isEmpty()) {
                return@update state.copy(
                    data = state.data.copy(
                        sendAmount = "",
                        receiveAmount = ""
                    ),
                    errorMessage = null
                )
            }

            val (calculatedResult, errorMsg) = performCalculation(sanitizedAmount, state.data.currentRate)

            state.copy(
                data = state.data.copy(
                    sendAmount = sanitizedAmount,
                    receiveAmount = calculatedResult
                ),
                errorMessage = errorMsg
            )
        }
    }

    private fun sanitizeInput(amount: String): String {
        return if (amount.length > 1 && amount.startsWith("0") && !amount.startsWith("0.")) {
            amount.trimStart('0')
        } else {
            amount
        }
    }

    private fun performCalculation(amount: String, rate: Double): Pair<String, String?> {
        val result = calculateExchangeUseCase(amount, rate)

        return result.fold(
            onSuccess = { FormatUtil.formatAmount(it) to null },
            onFailure = { "" to (it.message ?: "계산 오류") }
        )
    }
}