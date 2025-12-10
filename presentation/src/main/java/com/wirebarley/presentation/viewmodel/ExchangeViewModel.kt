package com.wirebarley.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wirebarley.domain.common.ApiResult
import com.wirebarley.domain.common.FormatUtil
import com.wirebarley.domain.common.UiState
import com.wirebarley.domain.model.Currency
import com.wirebarley.domain.model.ExchangeData
import com.wirebarley.domain.usecase.CalculateExchangeUseCase
import com.wirebarley.domain.usecase.GetExchangeRatesUseCase
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
                when (result) {
                    is ApiResult.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is ApiResult.Success -> {
                        val rates = result.data.rates
                        val currentRate = rates[Currency.KRW] ?: 0.0

                        _uiState.update { state ->
                            state.copy(
                                isLoading = false,
                                data = state.data.copy(
                                    exchangeRates = rates,
                                    currentRate = currentRate,
                                    timestamp = result.data.timestamp,
                                    formattedDate = FormatUtil.formatTimestamp(result.data.timestamp)
                                ),
                                errorMessage = null
                            )
                        }
                    }

                    is ApiResult.Error -> {
                        _uiState.update { state ->
                            state.copy(
                                isLoading = false,
                                errorMessage = result.message
                            )
                        }
                    }
                }
            }
        }
    }

    fun selectCurrency(currency: Currency) {
        val newRate = _uiState.value.data.exchangeRates[currency] ?: 0.0

        _uiState.update { state ->
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
        val sanitizedAmount =
            if (amount.length > 1 && amount.startsWith("0") && !amount.startsWith("0.")) {
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

        calculateExchange(sanitizedAmount, _uiState.value.data.currentRate)
    }

    private fun calculateExchange(amount: String, rate: Double) {
        val result = calculateExchangeUseCase(amount, rate)

        result.onSuccess { calculatedAmount ->
            _uiState.update { state ->
                state.copy(
                    data = state.data.copy(
                        receiveAmount = FormatUtil.formatAmount(calculatedAmount)
                    ),
                    errorMessage = null
                )
            }
        }.onFailure { exception ->
            _uiState.update { state ->
                state.copy(
                    data = state.data.copy(receiveAmount = ""),
                    errorMessage = exception.message
                )
            }
        }
    }
}