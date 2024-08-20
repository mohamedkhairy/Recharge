package com.example.recharge.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.recharge.presentation.screens.RechargeRoute
import com.example.sharedData.model.RechargeModel
import com.example.utils.core.jsonParse

const val RECHARGE_ROUTE_BASE = "recharge_route"

const val RECHARGE_DATA_ARG = "RECHARGE_DATA_ARG"

const val RECHARGE_ROUTE = "$RECHARGE_ROUTE_BASE?$RECHARGE_DATA_ARG={$RECHARGE_DATA_ARG}"

fun NavController.navigateToRecharge(rechargeModel: String?, navOptions: NavOptions? = null) {
    val route = if (!rechargeModel.isNullOrEmpty()) {
        "$RECHARGE_ROUTE_BASE?$RECHARGE_DATA_ARG=$rechargeModel"
    } else {
        RECHARGE_ROUTE_BASE
    }
    navigate(route, navOptions)
}


fun NavGraphBuilder.rechargeScreen(
    onBackClick: () -> Unit
) {
    composable(
        route = RECHARGE_ROUTE,
        arguments = listOf(
            navArgument(RECHARGE_DATA_ARG) {
                type = NavType.StringType
            },
        ),
    ) {

        val article = it.arguments?.getString(RECHARGE_DATA_ARG)
        RechargeRoute(
            rechargeModel = article?.jsonParse<RechargeModel>(),
            onBackClick = onBackClick
        )
    }
}
