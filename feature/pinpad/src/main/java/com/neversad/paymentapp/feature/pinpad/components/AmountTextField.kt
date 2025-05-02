package com.neversad.paymentapp.feature.pinpad.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neversad.paymentapp.core.ui.theme.shape

@Composable
internal fun AmountTextField(
    amount: String,
    onClear: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = MaterialTheme.shapes.medium,
                ),
    ) {
        OutlinedTextField(
            modifier = modifier,
            value = amount,
            onValueChange = {},
            leadingIcon = {
                Spacer(modifier = Modifier.minimumInteractiveComponentSize())
            },
            trailingIcon = {
                if (amount != "0.00") {
                    IconButton(onClick = onClear) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear amount",
                        )
                    }
                }
            },
            colors =
                OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent, // hide default
                    focusedBorderColor = Color.Transparent,
                ),
            shape = MaterialTheme.shapes.medium,
            readOnly = true,
            singleLine = true,
            textStyle =
                MaterialTheme.typography.headlineLarge.copy(
                    textAlign = TextAlign.Center,
                ),
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun AmountTextFieldPreview() {
    AmountTextField(
        modifier = Modifier.fillMaxWidth(),
        amount = "100.00",
    )
}

@Composable
@Preview(showBackground = true)
private fun AmountTextFieldEmptyPreview() {
    AmountTextField(
        modifier = Modifier.fillMaxWidth(),
        amount = "0.00",
    )
}
