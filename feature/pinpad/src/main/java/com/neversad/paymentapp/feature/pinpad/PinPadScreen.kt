package com.neversad.paymentapp.feature.pinpad

import android.content.res.Configuration
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.neversad.paymentapp.core.domain.common.Failure
import com.neversad.paymentapp.core.ui.components.LoadingScreen
import com.neversad.paymentapp.core.ui.theme.PaymentAppTheme
import com.neversad.paymentapp.feature.pinpad.components.AmountTextField
import com.neversad.paymentapp.feature.pinpad.components.PinPadHeader
import com.neversad.paymentapp.feature.pinpad.components.PinPadKeyboard


@Composable
internal fun PinPadRoute(
    navigateToReceipt: (String) -> Unit,
    viewModel: PinPadViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val effect by viewModel.effect.collectAsStateWithLifecycle(null)

    LaunchedEffect(effect) {
        when (val navigateToReceiptEffect = effect) {
            is PinPadEffect.NavigateToReceipt -> {
                navigateToReceipt(navigateToReceiptEffect.transactionId)
            }
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
    onAction: (PinPadAction) -> Unit,
    orientation: Int = LocalConfiguration.current.orientation

) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.failure) {
        state.failure?.let { failure ->
            snackbarHostState.showSnackbar(
                message = failure.message,
                duration = SnackbarDuration.Short,
                withDismissAction = true,
            ).also {
                onAction(PinPadAction.ClearFailure)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        LoadingScreen(
            isLoading = state.isLoading,
            modifier = Modifier
                .fillMaxSize()
        ) {


            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    PinPadHeader(
                        amount = state.formattedAmount,
                        onClear = {
                            onAction(PinPadAction.ClearAmount)
                        },
                        modifier = Modifier.weight(1f)
                    )

                    PinPadKeyboard(
                        onDigitClick = { digit ->
                            onAction(PinPadAction.EnterDigit(digit.toString()))
                        },
                        onSubmit = {
                            onAction(PinPadAction.Submit)
                        },
                        modifier = Modifier
                            .weight(0.8f)
                    )

                }
            } else {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    PinPadHeader(
                        amount = state.formattedAmount,
                        onClear = {
                            onAction(PinPadAction.ClearAmount)
                        },
                        modifier = Modifier.weight(1f)
                    )

                    PinPadKeyboard(
                        onDigitClick = { digit ->
                            onAction(PinPadAction.EnterDigit(digit.toString()))
                        },
                        onSubmit = {
                            onAction(PinPadAction.Submit)
                        },
                        modifier = Modifier
                            .weight(1.1f)
                    )

                }
            }

        }
    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PinPadScreenPreview() {
    PaymentAppTheme {
        PinPadScreen(
            state = PinPadState(),
            onAction = {},
        )
    }
}

@Composable
@Preview(
    showBackground = true, showSystemUi = true,
    device = "spec:parent=pixel_5,orientation=landscape"
)
fun PinPadScreenPreviewLandscape() {
    PaymentAppTheme {
        PinPadScreen(
            state = PinPadState(),
            onAction = {},
        )
    }
}

