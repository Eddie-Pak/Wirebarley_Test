package com.wirebarley.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ExchangeResultText(
    receiveAmount: String,
    currencyCode: String,
    errorMessage: String?,
    modifier: Modifier,
) {
    when {
        errorMessage != null -> {
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.error,
                ),
                modifier = modifier,
            )
        }
        receiveAmount.isNotEmpty() -> {
            Text(
                text = "수취금액은 $receiveAmount $currencyCode 입니다.",
                style = MaterialTheme.typography.titleMedium,
                modifier = modifier,
            )
        }
    }
}