package com.ck.car2.graphs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.ck.car2.ui.home.*

@Composable
fun HomeNavGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = "home0"
    ) {
        composable(route = "home0") {
            HomeScreen0(navController = navController)
        }
        composable(route = "home1") {
            HomeScreen1(navController = navController)
        }
        composable(route = "home2") {
            HomeScreen2(navController = navController)
        }
        composable(route = "home3") {
            HomeScreen3(navController = navController)
        }
        composable(route = "home4") {
            HomeScreen4(navController = navController)
        }
    }
}