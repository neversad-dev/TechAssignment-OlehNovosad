package com.neversad.paymentapp.feature.pinpad

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.neversad.paymentapp.core.ui.components.LoadingScreen
import com.neversad.paymentapp.core.ui.components.OrientationAware
import com.neversad.paymentapp.core.ui.theme.PaymentAppTheme
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
            val isHorizontal =
                LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
            OrientationAware(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                val headerWeight = if (isHorizontal) 1f else 1f
                val footerWeight = if (isHorizontal) 0.8f else 1.1f
                PinPadHeader(
                    amount = state.formattedAmount,
                    onClear = {
                        onAction(PinPadAction.ClearAmount)
                    },
                    modifier = Modifier
                        .weight(headerWeight)
                        .headerInsets()
                )

                PinPadKeyboard(
                    onDigitClick = { digit ->
                        onAction(PinPadAction.EnterDigit(digit.toString()))
                    },
                    onSubmit = {
                        onAction(PinPadAction.Submit)
                    },
                    modifier = Modifier
                        .weight(footerWeight)
                        .footerInsets(),

                    )
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

