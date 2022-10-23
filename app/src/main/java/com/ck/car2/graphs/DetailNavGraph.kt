package com.ck.car2.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.ck.car2.ui.detail.DetailScreen

fun NavGraphBuilder.detailGraph(navController: NavController) {
    navigation(startDestination = "detail", route = Graph.DETAIL) {
        composable("detail") {
            DetailScreen()
        }
    }
}