package com.wirebarley.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun WirebarleySpacer(spacing: Dp) {
    Spacer(modifier = Modifier.height(spacing))
}