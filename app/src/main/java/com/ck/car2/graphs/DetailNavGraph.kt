package com.ck.car2.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.ck.car2.ui.detail.DetailScreen

fun NavGraphBuilder.detailGraph() {
    navigation(startDestination = "detail", route = Graph.DETAIL.plus("/{productId}/{secondId}")) {
        composable(
            route = "detail",
        ) {
            val productId = it.arguments?.getString("productId")
            val secondId = it.arguments?.getString("secondId")
            DetailScreen(productId.plus(secondId))
        }
    }
}
