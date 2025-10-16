package com.recruitment.ratemate.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.recruitment.currency.list.CurrencyListScreen
import com.recruitment.details.CurrencyDetailsScreen

@Composable
fun RateMateNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "currency_list",
        modifier = modifier
    ) {
        composable("currency_list") {
            CurrencyListScreen(
                onCurrencyClick = { currencyCode, table ->
                    navController.navigate("currency_details/$currencyCode/$table")
                }
            )
        }
        composable("currency_details/{currencyCode}/{table}") {
            CurrencyDetailsScreen()
        }
    }
}
