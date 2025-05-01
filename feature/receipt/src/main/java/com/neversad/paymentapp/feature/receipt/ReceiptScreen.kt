package com.neversad.paymentapp.feature.receipt

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ReceiptRoute() {
    ReceiptScreen()
}

@Composable
fun ReceiptScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Welcome to Receipt Screen")
    }
}

@Preview(showBackground = true)
@Composable
fun ReceiptPreview() {
    ReceiptScreen()
}