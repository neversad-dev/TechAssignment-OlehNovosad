package com.neversad.paymentapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.neversad.paymentapp.feature.pinpad.PinPad
import com.neversad.paymentapp.feature.pinpad.PinPadRoute
import com.neversad.paymentapp.feature.pinpad.pinPadScreen
import com.neversad.paymentapp.feature.pinpad.navigateToPinPad
import com.neversad.paymentapp.feature.receipt.Receipt
import com.neversad.paymentapp.feature.receipt.navigateToReceipt
import com.neversad.paymentapp.feature.receipt.receiptScreen
import kotlin.reflect.KClass

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    startDestination: KClass<*> = PinPad::class
) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        pinPadScreen(navigateToReceipt = navController::navigateToReceipt)
        receiptScreen(onNavigateBack = navController::popBackStack)
    }
}
