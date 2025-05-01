package com.neversad.paymentapp.feature.pinpad

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class PinPad(val userId: String)

fun NavController.navigateToPinPad(userId: String, navOptions: NavOptions? = null) {
    navigate(PinPad(userId), navOptions)  // Type safe navigation
}

fun NavGraphBuilder.pinPadScreen() {
    composable<PinPad> {
        // Using savedStateHandle in VM can be better
        val userId = it.toRoute<PinPad>().userId  // Type safe access
        PinPadRoute(userId)
    }
}
