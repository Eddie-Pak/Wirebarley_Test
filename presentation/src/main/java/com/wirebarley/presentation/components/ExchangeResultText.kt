package com.wirebarley.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.wirebarley.presentation.R

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
                text = stringResource(R.string.receive_amount_format, receiveAmount, currencyCode),
                style = MaterialTheme.typography.titleMedium,
                modifier = modifier,
            )
        }
    }
}