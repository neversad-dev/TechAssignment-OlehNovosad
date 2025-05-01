package com.neversad.paymentapp.feature.receipt

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object Receipt

fun NavController.navigateToReceipt(navOptions: NavOptions? = null) {
    navigate(Receipt, navOptions)
}

fun NavGraphBuilder.receiptScreen() {
    composable<Receipt> {
        ReceiptRoute()
    }
}
