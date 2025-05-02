package com.neversad.paymentapp.feature.receipt

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
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.neversad.paymentapp.core.model.Transaction
import com.neversad.paymentapp.core.ui.components.LoadingScreen
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
            topBar = {
                TopAppBar(
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                TransactionDetails(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    transaction = state.transaction
                )
            }
        }
    }
}

@Composable
private fun TransactionDetails(
    modifier: Modifier = Modifier,
    transaction: Transaction
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .padding(bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Transaction Details",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        TransactionDetailRow("Transaction ID", transaction.id)
        TransactionDetailRow("Status", transaction.status.name)

        val purchaseAmount = transaction.purchaseAmount.toDoubleOrNull() ?: 0.0
        val taxAmount = (purchaseAmount * (transaction.taxRate.toDoubleOrNull() ?: 0.0) / 100)
        val tipAmount = transaction.tipAmount.toDoubleOrNull() ?: 0.0
        val discountAmount = transaction.discountAmount.toDoubleOrNull() ?: 0.0
        val finalAmount = purchaseAmount + taxAmount + tipAmount - discountAmount

        TransactionDetailRow("Purchase Amount", formatCurrency(purchaseAmount))
        TransactionDetailRow("Tax", formatCurrency(taxAmount))
        TransactionDetailRow("Tip", formatCurrency(tipAmount))
        TransactionDetailRow("Discount", formatCurrency(discountAmount))
        TransactionDetailRow(
            "Final Amount",
            formatCurrency(finalAmount),
            isHighlighted = true
        )

        val date = try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
            val parsedDate = inputFormat.parse(transaction.timestamp)
            parsedDate?.let { outputFormat.format(it) } ?: transaction.timestamp
        } catch (e: Exception) {
            transaction.timestamp
        }

        TransactionDetailRow("Date", date)
    }
}

@Composable
private fun TransactionDetailRow(
    label: String,
    value: String,
    isHighlighted: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = if (isHighlighted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = if (isHighlighted) FontWeight.Bold else FontWeight.Normal,
            color = if (isHighlighted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
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

@Preview(showBackground = true)
@Composable
fun ReceiptPreviewLoading() {
    PaymentAppTheme {
        ReceiptScreen(
            state = ReceiptState(
                isLoading = true,
            ),
            onAction = {}
        )
    }
}

