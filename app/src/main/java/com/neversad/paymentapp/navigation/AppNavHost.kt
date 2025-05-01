package com.neversad.paymentapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.neversad.paymentapp.feature.pinpad.homeScreen
import com.neversad.paymentapp.feature.pinpad.navigateToHome
import com.neversad.paymentapp.feature.receipt.Login
import com.neversad.paymentapp.feature.receipt.loginScreen
import kotlin.reflect.KClass

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    startDestination: KClass<*> = Login::class
) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        homeScreen()
        loginScreen(navigateToHome = navController::navigateToHome)
    }
}
