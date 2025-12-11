package com.wirebarley.presentation

import app.cash.turbine.test
import com.wirebarley.domain.common.ApiResult
import com.wirebarley.domain.model.Currency
import com.wirebarley.domain.model.ExchangeRate
import com.wirebarley.domain.usecase.CalculateExchangeUseCase
import com.wirebarley.domain.usecase.GetExchangeRatesUseCase
import com.wirebarley.presentation.util.MainDispatcherRule
import com.wirebarley.presentation.viewmodel.ExchangeViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ExchangeViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getExchangeRatesUseCase = mockk<GetExchangeRatesUseCase>()
    private val calculateExchangeUseCase = mockk<CalculateExchangeUseCase>()
    private lateinit var viewModel: ExchangeViewModel

    private val dummyRates = mapOf(
        Currency.KRW to 1100.0,
        Currency.JPY to 100.0,
        Currency.PHP to 50.0
    )
    private val dummyExchangeRate = ExchangeRate(dummyRates, 1640995200L)

    @Before
    fun setup() {
        coEvery { getExchangeRatesUseCase() } returns flowOf(ApiResult.Success(dummyExchangeRate))
    }

    @Test
    fun `초기 선택 통화는 KRW`() = runTest {
        every { getExchangeRatesUseCase() } returns flowOf(ApiResult.Loading)

        viewModel = ExchangeViewModel(getExchangeRatesUseCase, calculateExchangeUseCase)

        assertEquals(Currency.KRW, viewModel.uiState.value.data.selectedCurrency)
    }

    @Test
    fun `환율_로딩_성공_시_환율_데이터가_정상적으로_저장되어야_한다`() = runTest {
        // When
        viewModel = ExchangeViewModel(getExchangeRatesUseCase, calculateExchangeUseCase)

        // Then
        viewModel.uiState.test {
            val item = awaitItem()

            assertEquals(false, item.isLoading)
            assertEquals(dummyRates, item.data.exchangeRates)
            assertEquals(1100.0, item.data.currentRate, 0.0)
            assertEquals(1640995200L, item.data.timestamp)
            assertNull(item.errorMessage)
        }
    }

    @Test
    fun `통화_선택_시_selectedCurrency와_currentRate가_변경되어야_한다`() = runTest {
        viewModel = ExchangeViewModel(getExchangeRatesUseCase, calculateExchangeUseCase)

        viewModel.selectCurrency(Currency.JPY)

        viewModel.uiState.test {
            val item = awaitItem()

            assertEquals(Currency.JPY, item.data.selectedCurrency)
            assertEquals(100.0, item.data.currentRate, 0.0)

            assertEquals("", item.data.sendAmount)
            assertEquals("", item.data.receiveAmount)
        }
    }

    @Test
    fun `정상_금액_입력_시_sendAmount가_업데이트되고_수취금액이_계산되어야_한다`() = runTest {
        val inputAmount = "100"
        val calculatedResult = 110000.0

        every { calculateExchangeUseCase(inputAmount, 1100.0) } returns Result.success(calculatedResult)

        viewModel = ExchangeViewModel(getExchangeRatesUseCase, calculateExchangeUseCase)

        viewModel.updateSendAmount(inputAmount)

        viewModel.uiState.test {
            val item = awaitItem()

            assertEquals(inputAmount, item.data.sendAmount)
            assertEquals("110000.00", item.data.receiveAmount)
            assertNull(item.errorMessage)
        }
    }
}