package com.neversad.paymentapp.feature.receipt

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data class Receipt(val transactionId: String)

fun NavController.navigateToReceipt(
    transactionId: String,
    navOptions: NavOptions? = null
) {
    navigate(Receipt(transactionId), navOptions)
}

fun NavGraphBuilder.receiptScreen(
    onNavigateBack: () -> Unit,
) {
    composable<Receipt> {
        ReceiptRoute(onNavigateBack)
    }
}
