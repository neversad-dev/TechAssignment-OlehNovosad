package com.neversad.paymentapp.feature.pinpad

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
object PinPad

fun NavController.navigateToPinPad(navOptions: NavOptions? = null) {
    navigate(PinPad, navOptions)  // Type safe navigation
}

fun NavGraphBuilder.pinPadScreen(navigateToReceipt: (String) -> Unit) {
    composable<PinPad> {
        // Using savedStateHandle in VM can be better
        PinPadRoute(navigateToReceipt)
    }
}
