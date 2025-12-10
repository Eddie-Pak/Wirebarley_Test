package com.wirebarley.presentation.viewmodel

import app.cash.turbine.test
import com.wirebarley.domain.model.Currency
import com.wirebarley.presentation.util.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ExchangeViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: ExchangeViewModel

    @Before
    fun setup() {
        viewModel = ExchangeViewModel()
    }

    @Test
    fun `입력값이 10000을 초과하면 에러 메시지가 표시되어야 한다`() = runTest {
        viewModel.uiState.test {
            awaitItem()

            viewModel.updateSendAmount("10001")

            skipItems(1)

            val errorState = awaitItem() // 최종 에러 상태 가져오기

            assertEquals("송금액이 바르지 않습니다", errorState.errorMessage)
        }
    }

    @Test
    fun `정상적인_금액(100)_입력_시_수취금액이_계산되어야_한다`() = runTest {
        viewModel.uiState.test {
            awaitItem()

            viewModel.updateSendAmount("100")

            skipItems(1)

            // mock data  100 * 1350.232 = 135,023.20 (KRW)
            val state = awaitItem()
            assertEquals("100", state.data.sendAmount)
            assertEquals("135,023.20", state.data.receiveAmount)
            assertNull(state.errorMessage)
        }
    }

    @Test
    fun `통화를_PHP로_변경하면_업데이트되어야_한다`() = runTest {
        viewModel.uiState.test {
            awaitItem()

            viewModel.updateSendAmount("100")

            skipItems(1)

            viewModel.selectCurrency(Currency.PHP)

            skipItems(1)

            val phpState = awaitItem()

            assertEquals(Currency.PHP, phpState.data.selectedCurrency)
        }
    }
}