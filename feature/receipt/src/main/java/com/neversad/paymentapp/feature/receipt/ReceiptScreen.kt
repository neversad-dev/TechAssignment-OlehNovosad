package com.neversad.paymentapp.feature.receipt

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.neversad.paymentapp.core.model.Transaction
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
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Transaction Receipt") },
                navigationIcon = {
                    IconButton(onClick = { onAction(ReceiptAction.NavigateBack) }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
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
            when (state) {
                is ReceiptState.Loading -> {
                    CircularProgressIndicator()
                }
                is ReceiptState.Success -> {
                    TransactionDetails(state.transaction)
                }
                is ReceiptState.Error -> {
                    LaunchedEffect(snackbarHostState) {
                        snackbarHostState.showSnackbar(
                            message = state.failure.message,
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TransactionDetails(transaction: Transaction) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
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
    ReceiptScreen(
        state = ReceiptState.Success(
            Transaction(
                id = "123",
                status = Transaction.Status.SUCCESS,
                purchaseAmount = "100.00",
                taxableAmount = "100.00",
                taxRate = "10.00",
                tipAmount = "15.00",
                discountAmount = "5.00",
                timestamp = "2024-03-20T10:30:00Z"
            )
        ),
        onAction = {}
    )
}