package com.neversad.paymentapp.feature.pinpad

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun PinPadRoute(
    navigateToReceipt: () -> Unit,
    viewModel: PinPadViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val effect by viewModel.effect.collectAsStateWithLifecycle(null)

    LaunchedEffect(effect) {
        when (effect) {
            is PinPadEffect.NavigateToReceipt -> navigateToReceipt()
            null -> {}
        }
    }

    PinPadScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun PinPadScreen(
    state: PinPadState,
    onAction: (PinPadAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 32.dp)
        ) {
            Text(
                text = "Purchase",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF2C2C2C)
                )
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Please enter amount.",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color(0xFF6B6B6B)
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                color = Color.White,
                tonalElevation = 1.dp
            ) {
                Text(
                    text = if (state.amount.isEmpty()) "0.00" else state.amount.toAmountFormat(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF2C2C2C)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (i in 1..3) {
                    NumberButton(
                        number = i.toString(),
                        onClick = { onAction(PinPadAction.EnterDigit(i.toString())) }
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (i in 4..6) {
                    NumberButton(
                        number = i.toString(),
                        onClick = { onAction(PinPadAction.EnterDigit(i.toString())) }
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (i in 7..9) {
                    NumberButton(
                        number = i.toString(),
                        onClick = { onAction(PinPadAction.EnterDigit(i.toString())) }
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Spacer(
                    modifier =Modifier.size(72.dp)
                )
                
                NumberButton(
                    number = "0",
                    onClick = { onAction(PinPadAction.EnterDigit("0")) }
                )
                ActionButton(
                    text = "OK",
                    onClick = { onAction(PinPadAction.Submit) }
                )
            }
        }
    }
}

@Composable
private fun NumberButton(
    number: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        onClick = onClick,
        modifier = modifier.size(72.dp),
        colors = ButtonDefaults.textButtonColors(
            contentColor = Color(0xFF2C2C2C)
        )
    ) {
        Text(
            text = number,
            fontSize = 32.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
private fun ActionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.size(width = 72.dp, height = 48.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF64B5A2)
        )
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

private fun String.toAmountFormat(): String {
    return if (this.length <= 2) {
        "0.${this.padStart(2, '0')}"
    } else {
        "${this.substring(0, this.length - 2)}.${this.substring(this.length - 2)}"
    }
}

@Composable
@Preview(showBackground = true)
fun PinPadScreenPreview() {
    PinPadScreen(
        state = PinPadState(),
        onAction = {}
    )
}
