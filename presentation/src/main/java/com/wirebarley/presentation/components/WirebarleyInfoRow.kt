package com.wirebarley.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.wirebarley.domain.model.Currency
import com.wirebarley.presentation.ui.theme.WirebarleyDimens

@Composable
fun InfoRow(label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "$label : ",
            modifier = Modifier.width(WirebarleyDimens.Width.Small),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.End
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun InfoRowWithSelector(
    selectedCurrency: Currency,
    onCurrencySelected: (Currency) -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "수취국가 : ",
            modifier = Modifier.width(WirebarleyDimens.Width.Small),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.End
        )

        CurrencySelector(
            selectedCurrency = selectedCurrency,
            onCurrencySelected = onCurrencySelected
        )
    }
}

@Composable
fun InfoRowWithAmount(amount: String, onValueChange: (String) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "송금액 : ",
            modifier = Modifier.width(WirebarleyDimens.Width.Small),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.End
        )

        BasicTextField(
            value = amount,
            onValueChange = onValueChange,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                textAlign = TextAlign.End,
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .size(
                    width = WirebarleyDimens.Width.Medium,
                    height = WirebarleyDimens.Height.Small
                ),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .border(
                            width = WirebarleyDimens.Width.oneDp,
                            color = MaterialTheme.colorScheme.outline,
                            shape = RoundedCornerShape(WirebarleyDimens.Shape.Small)
                        )
                        .padding(horizontal = WirebarleyDimens.Padding.Medium),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    innerTextField()
                }
            }
        )

        Text(text = " USD")
    }
}