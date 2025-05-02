package com.neversad.paymentapp.feature.receipt

import android.content.res.Configuration
import android.preference.PreferenceActivity.Header
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.neversad.paymentapp.core.model.Transaction
import com.neversad.paymentapp.core.ui.components.LoadingScreen
import com.neversad.paymentapp.core.ui.components.OrientationAware
import com.neversad.paymentapp.core.ui.theme.PaymentAppTheme
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ReceiptRoute(
    onNavigateBack: () -> Unit,
    viewModel: ReceiptViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ReceiptScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is ReceiptAction.NavigateBack -> {
                    onNavigateBack()
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiptScreen(
    state: ReceiptState,
    onAction: (ReceiptAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember { SnackbarHostState() }


    LaunchedEffect(state.failure) {
        state.failure?.let {
            snackbarHostState.showSnackbar(
                message = state.failure.message,
                duration = SnackbarDuration.Indefinite,
                withDismissAction = true
            ).also {
                onAction(ReceiptAction.NavigateBack)
            }
        }
    }

    LoadingScreen(
        isLoading = state.isLoading,
        modifier = modifier
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            contentWindowInsets = WindowInsets(0),
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    ),
                    title = { },
                    navigationIcon = {
                        IconButton(onClick = { onAction(ReceiptAction.NavigateBack) }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            TransactionDetails(
                modifier = Modifier
                    .fillMaxSize(),
                transaction = state.transaction
            )
        }
    }
}

@Composable
private fun TransactionDetails(
    modifier: Modifier = Modifier,
    transaction: Transaction
) {

    val isHorizontal = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    OrientationAware(
        modifier = modifier
            .fillMaxSize()
    ) {

        val headerWeight = if (isHorizontal) 1f else 0.6f
        val footerWeight = if (isHorizontal) 0.8f else 1f
        Header(
            modifier = Modifier
                .fillMaxSize()
                .weight(headerWeight)
                .padding(horizontal = 16.dp)
                .headerInsets(),
            transaction = transaction,
            verticalArrangement = if (isHorizontal) Arrangement.Center else Arrangement.Bottom,
            horizontalAlignment = if (isHorizontal) Alignment.Start else Alignment.CenterHorizontally
        )

        val paddingHorizontal = if (isHorizontal) 64.dp else 16.dp
        val background =
            if (isHorizontal) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface
        Footer(
            modifier = Modifier
                .fillMaxSize()
                .weight(footerWeight)
                .background(color = background)
                .padding(horizontal = paddingHorizontal)
                .footerInsets(),
            verticalArrangement = if (isHorizontal) Arrangement.Center else Arrangement.Top,
            transaction = transaction
        )
    }
}

@Composable
private fun Header(
    modifier: Modifier = Modifier,
    transaction: Transaction,
    verticalArrangement: Arrangement.Vertical = Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally
) {
    Column(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment
    ) {

        Text(
            text = "Transaction Details",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        val date = try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
            val parsedDate = inputFormat.parse(transaction.timestamp)
            parsedDate?.let { outputFormat.format(it) } ?: transaction.timestamp
        } catch (e: Exception) {
            transaction.timestamp
        }
        Text(
            modifier = Modifier.padding(bottom = 24.dp),
            text = date,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
private fun Footer(
    modifier: Modifier = Modifier,
    transaction: Transaction,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top
) {
    Column(
        modifier = modifier,
        verticalArrangement = verticalArrangement
    ) {
        TransactionDetailRow("Transaction ID", transaction.id)
        TransactionDetailRow(
            "Transaction Status",
            transaction.status.name,
            transaction.status != Transaction.Status.SUCCESS
        )

        val purchaseAmount = transaction.purchaseAmount.toDoubleOrNull() ?: 0.0
        val taxAmount = (purchaseAmount * (transaction.taxRate.toDoubleOrNull() ?: 0.0) / 100)
        val tipAmount = transaction.tipAmount.toDoubleOrNull() ?: 0.0
        val discountAmount = transaction.discountAmount.toDoubleOrNull() ?: 0.0
        val finalAmount = purchaseAmount + taxAmount + tipAmount - discountAmount

        TransactionDetailRow(
            "Final Amount",
            formatCurrency(finalAmount),
            isHighlighted = true
        )

        TransactionDetailRow("Tax", formatCurrency(taxAmount))
    }
}

@Composable
private fun TransactionDetailRow(
    label: String,
    value: String,
    isHighlighted: Boolean = false,
    isError: Boolean = false,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val textColor = when {
            isError -> MaterialTheme.colorScheme.error
            isHighlighted -> MaterialTheme.colorScheme.primary
            else -> MaterialTheme.colorScheme.onSurface
        }
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = textColor
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = if (isHighlighted) FontWeight.Bold else FontWeight.Normal,
            color = textColor
        )
    }
}

private fun formatCurrency(amount: Double): String {
    return NumberFormat.getCurrencyInstance().format(amount)
}

@Preview(showBackground = true)
@Composable
fun ReceiptPreview() {
    PaymentAppTheme {
        ReceiptScreen(
            state = ReceiptState(
                isLoading = false,
            ),
            onAction = {}
        )
    }
}

@Preview(showBackground = true, device = "spec:parent=pixel_5,orientation=landscape")
@Composable
fun ReceiptPreviewLoading() {
    PaymentAppTheme {
        ReceiptScreen(
            state = ReceiptState(
                isLoading = false,
            ),
            onAction = {}
        )
    }
}

