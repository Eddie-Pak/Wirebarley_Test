package com.wirebarley.presentation.ui.theme

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

object WirebarleyDimens {
    object Padding {
        val Small = 4.dp
        val Medium = 8.dp
        val Large = 16.dp
    }

    object Spacing {
        val Small = 8.dp
        val Medium = 16.dp
        val Large = 32.dp
        val xLarge = 48.dp
    }

    object Width {
        val oneDp = 1.dp
        val Small = 72.dp
        val Medium = 100.dp
        val Large = 120.dp
    }

    object Height {
        val Small = 28.dp
        val Medium = 42.dp
    }

    object Shape {
        val Small = 4.dp
    }
}

fun Modifier.defaultPadding(): Modifier = this.padding(horizontal = 24.dp, vertical = 48.dp)