package com.ck.car2.graphs

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ck.car2.CarByComposeAppState
import com.ck.car2.ui.home.*
import com.ck.car2.viewmodels.HomeViewModel

@Composable
fun HomeNavGraph(
    homeViewModel: HomeViewModel,
    navController: NavHostController,
    rootController: NavController,
    appState: CarByComposeAppState,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "home0",
//        modifier = modifier.padding(bottom = SysUI),
        route = Graph.HOME,
    ) {
        composable(route = "home0") {
            HomeScreen0(
                homeViewModel = homeViewModel,
                navController = navController,
                rootController = rootController,
                appState = appState
            )
        }
        composable(route = "home1") {
            HomeScreen1(
                homeViewModel = homeViewModel,
                navController = navController)
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