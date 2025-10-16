package com.recruitment.ratemate.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.recruitment.currency.list.CurrencyListScreen

@Composable
fun RateMateNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "currency_list",
        modifier = modifier
    ) {
        composable("currency_list") {
            CurrencyListScreen()
        }
    }
}
