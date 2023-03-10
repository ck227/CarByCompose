package com.ck.car2.graphs

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ck.car2.CarByComposeAppState
import com.ck.car2.rememberCarByComposeAppState
import com.ck.car2.ui.home.HomeScreen
import com.ck.car2.ui.home.LaunchScreen

@Composable
fun RootNavigationGraph(
//    appContainer: AppContainer,
    navController: NavHostController,
    appState: CarByComposeAppState = rememberCarByComposeAppState()
) {
    //appState应该是使用compositionLocal来传递
    NavHost(
        navController = navController,
        startDestination = Graph.LAUNCH,
        modifier = Modifier.navigationBarsPadding()
    ) {
        //启动页面，跳转至主页面
        composable(route = Graph.LAUNCH) {
            LaunchScreen(onTimeOut = {
                navController.navigate(Graph.HOME) {
                    popUpTo(Graph.LAUNCH) {
                        inclusive = true
                    }
                }
            })
        }
        //主页面，跳转至详情
        composable(route = Graph.HOME) {
            HomeScreen(
                appState = appState,
                navigateToDetail = {
                    navController.navigate(Graph.DETAIL.plus("/wocao/666"))
                }
            )
        }
        detailGraph()
    }
}

object Graph {
    const val LAUNCH = "launch_graph"
    const val HOME = "home_graph"
    const val DETAIL = "detail_graph"
}