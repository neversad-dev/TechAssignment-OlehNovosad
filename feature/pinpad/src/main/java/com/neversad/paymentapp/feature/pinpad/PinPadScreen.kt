package com.neversad.paymentapp.feature.pinpad

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun PinPadRoute(
    navigateToReceipt: () -> Unit,
    viewModel: PinPadViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val effect by viewModel.effect.collectAsStateWithLifecycle()

    LaunchedEffect(effect) {
        when (effect) {
            is PinPadEffect.NavigateToReceipt -> navigateToReceipt()
            null -> {}
        }
    }

    PinPadScreen(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun PinPadScreen(
    state: PinPadState,
    onEvent: (PinPadEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Enter PIN",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = state.pin.map { "•" }.joinToString(""),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(vertical = 32.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (i in 1..3) {
                PinButton(number = i.toString(), onClick = { onEvent(PinPadEvent.OnPinDigitEntered(i.toString())) })
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (i in 4..6) {
                PinButton(number = i.toString(), onClick = { onEvent(PinPadEvent.OnPinDigitEntered(i.toString())) })
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (i in 7..9) {
                PinButton(number = i.toString(), onClick = { onEvent(PinPadEvent.OnPinDigitEntered(i.toString())) })
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            PinButton(number = "C", onClick = { onEvent(PinPadEvent.OnClearPin) })
            PinButton(number = "0", onClick = { onEvent(PinPadEvent.OnPinDigitEntered("0")) })
            PinButton(number = "OK", onClick = { onEvent(PinPadEvent.OnPinSubmitted) })
        }
    }
}

@Composable
private fun PinButton(
    number: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(72.dp),
        shape = CircleShape
    ) {
        Text(
            text = number,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PinPadScreenPreview() {
    PinPadScreen(
        state = PinPadState(),
        onEvent = {}
    )
}
