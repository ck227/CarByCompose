package com.ck.car2.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.ck.car2.ui.home.LaunchScreen

fun NavGraphBuilder.launchGraph(navController: NavController) {
    navigation(startDestination = "launch", route = Graph.LAUNCH) {
        composable("launch") {
            LaunchScreen(onTimeOut = {
                navController.navigate(Graph.HOME) {
                    launchSingleTop = true
                }
            })
        }
    }
}