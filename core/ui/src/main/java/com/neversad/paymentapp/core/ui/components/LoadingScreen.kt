package com.neversad.paymentapp.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neversad.paymentapp.core.ui.theme.PaymentAppTheme

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    content: @Composable BoxScope.() -> Unit
) {
    val blurBackground = remember(isLoading) {
        if (isLoading) {
            Modifier
                .background(Color.Black.copy(alpha = 0.2f))
                .blur(radius = 4.dp)
        } else {
            Modifier
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = blurBackground
        ) {
            content()
        }
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) { /* Block all pointer events */ }
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .width(64.dp)
                        .align(Alignment.Center),
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoadingScreenPreview() {
    PaymentAppTheme {
        LoadingScreen(
            isLoading = true
        ) {
            Text(
                "Content", modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoadingScreenPreview_NotLoading() {
    PaymentAppTheme {
        LoadingScreen(
            isLoading = false
        ) {
            Text(
                "Content", modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
            )
        }
    }
}