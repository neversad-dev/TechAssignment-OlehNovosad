package com.neversad.paymentapp.feature.pinpad

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PinPadRoute(navigateToReceipt: () -> Unit) {
    PinPadScreen {
        navigateToReceipt()
    }
}

@Composable
fun PinPadScreen(onClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = onClick) {
            Text("OK")
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PinPadScreenPreview() {
    PinPadScreen {}
}
