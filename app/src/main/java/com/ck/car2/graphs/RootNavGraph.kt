package com.ck.car2.graphs

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ck.car2.data.AppContainer
import com.ck.car2.ui.home.HomeScreen
import com.ck.car2.viewmodels.HomeViewModel

@Composable
fun RootNavigationGraph(
    appContainer:AppContainer,
    navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Graph.LAUNCH
    ) {
        launchGraph(navController)
        composable(route = Graph.HOME) {
            val homeViewModel: HomeViewModel = viewModel(
                factory = HomeViewModel.provideFactory(appContainer.homeRepository)
            )
            HomeScreen(
                homeViewModel = homeViewModel
            )
        }
    }
}

object Graph {
    const val LAUNCH = "launch_graph"
    const val HOME = "home_graph"
}