package com.ck.car2.graphs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.ck.car2.CarByComposeAppState
import com.ck.car2.ui.detail.DetailScreen
import com.ck.car2.viewmodels.HomeViewModel

fun NavGraphBuilder.detailGraph() {
    navigation(startDestination = "detail", route = Graph.DETAIL.plus("/{productId}/{secondId}")) {
        composable(
            route = "detail",
//            arguments = listOf(navArgument("productId") {
//                type = NavType.StringType
//            })
        ) {
            if (it.arguments != null) {
                val productId = it.arguments?.getString("productId")
                val secondId = it.arguments?.getString("secondId")
                DetailScreen(productId.plus(secondId))
            }

        }
    }
}
