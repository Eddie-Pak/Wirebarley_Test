package com.wirebarley.presentation.ui

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wirebarley.presentation.components.ExchangeResultText
import com.wirebarley.presentation.components.InfoRow
import com.wirebarley.presentation.components.InfoRowWithAmount
import com.wirebarley.presentation.components.InfoRowWithSelector
import com.wirebarley.presentation.components.WirebarleySpacer
import com.wirebarley.presentation.ui.theme.WirebarleyDimens
import com.wirebarley.presentation.ui.theme.defaultPadding
import com.wirebarley.presentation.viewmodel.ExchangeViewModel

@Composable
fun ExchangeScreen(
    modifier: Modifier = Modifier,
    viewModel: ExchangeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .defaultPadding()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
    ) {
        // 타이틀
        Text(
            text = "환율 계산",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        WirebarleySpacer(WirebarleyDimens.Spacing.Large)

        // 송금국가
        InfoRow("송금국가", "미국 (USD)")

        WirebarleySpacer(WirebarleyDimens.Spacing.Small)

        // 수취국가 선택
        InfoRowWithSelector(
            selectedCurrency = uiState.data.selectedCurrency,
            onCurrencySelected = { currency ->
                viewModel.selectCurrency(currency)
            }
        )

        WirebarleySpacer(WirebarleyDimens.Spacing.Small)

        // 환율
        InfoRow("환율", "${uiState.data.formattedRate} ${uiState.data.selectedCurrency.code} / USD")

        WirebarleySpacer(WirebarleyDimens.Spacing.Small)

        // 조회시간
        InfoRow("조회시간", uiState.data.queryTime)

        WirebarleySpacer(WirebarleyDimens.Spacing.Small)

        // 송금액 입력
        InfoRowWithAmount(
            amount = uiState.data.sendAmount,
            onValueChange = { amount ->
                if (amount == "00") return@InfoRowWithAmount

                viewModel.updateSendAmount(amount)
            }
        )

        WirebarleySpacer(WirebarleyDimens.Spacing.xLarge)

        // 수취금액 결과 또는 에러 메시지
        ExchangeResultText(
            receiveAmount = uiState.data.receiveAmount,
            currencyCode = uiState.data.selectedCurrency.code,
            errorMessage = uiState.errorMessage,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}