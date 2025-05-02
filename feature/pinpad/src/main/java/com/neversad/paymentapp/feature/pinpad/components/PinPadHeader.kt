package com.neversad.paymentapp.feature.pinpad.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neversad.paymentapp.core.ui.theme.PaymentAppTheme


@Composable
internal fun PinPadHeader(
    modifier: Modifier = Modifier,
    amount: String,
    onClear: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Purchase",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Please enter amount.",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color(0xFF6B6B6B)
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        AmountTextField(
            amount = amount,
            onClear = onClear,
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PinPadHeaderPreview() {
    PaymentAppTheme {
        PinPadHeader(
            amount = "100.00",
            onClear = {}
        )
    }
}