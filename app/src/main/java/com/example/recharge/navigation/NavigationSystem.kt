package com.example.recharge.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.home.presentation.navigation.HOME_ROUTE
import com.example.home.presentation.navigation.homeScreen
import com.example.recharge.presentation.navigation.navigateToRecharge
import com.example.recharge.presentation.navigation.rechargeScreen


@Composable
fun NavigationSystem() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HOME_ROUTE,
        builder = {
            homeScreen(
                onNextClick = {
                    navController.navigateToRecharge(it)
                }
            )
            rechargeScreen {
                navController.popBackStack(HOME_ROUTE, false)
            }
        }
    )

}





