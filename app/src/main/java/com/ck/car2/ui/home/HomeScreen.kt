package com.ck.car2.ui.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ck.car2.R
import com.ck.car2.graphs.HomeNavGraph
import com.ck.car2.ui.theme.CarByComposeTheme
import com.ck.car2.viewmodels.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        bottomBar = {
        MyBottomNavigation(navController = navController)
    }) { innerPadding ->
        HomeNavGraph(
            homeViewModel = homeViewModel,
            navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun MyBottomNavigation(navController: NavController, modifier: Modifier = Modifier) {
    BottomNavigation(
        backgroundColor = CarByComposeTheme.colors.homeBottomItemBg,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        screens.forEach { screen ->
            val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
            BottomNavigationItem(
                selected = selected,
                onClick = {
                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        screen.icon,
                        contentDescription = null,
                        tint = if (selected) CarByComposeTheme.colors.primary else CarByComposeTheme.colors.mainTabUnSelect
                    )
                },
                label = {
                    Text(
                        text = stringResource(screen.title),
                        color = if (selected) CarByComposeTheme.colors.primary else CarByComposeTheme.colors.mainTabUnSelect
                    )
                },
//                selectedContentColor = MaterialTheme.colors.error,
//                unselectedContentColor = MaterialTheme.colors.primary,
            )
        }
    }
}

sealed class Screen(val route: String, @StringRes val title: Int, val icon: ImageVector) {
    object Home0 : Screen(
        route = "home0", title = R.string.home_menu0, icon = Icons.Default.Home
    )

    object Home1 : Screen(
        route = "home1", title = R.string.home_menu1, icon = Icons.Default.Menu
    )

    object Home2 : Screen(
        route = "home2", title = R.string.home_menu2, icon = Icons.Default.Star
    )

    object Home3 : Screen(
        route = "home3", title = R.string.home_menu3, icon = Icons.Default.ShoppingCart
    )

    object Home4 : Screen(
        route = "home4", title = R.string.home_menu4, icon = Icons.Default.Person
    )
}

val screens = listOf(
    Screen.Home0,
    Screen.Home1,
    Screen.Home2,
    Screen.Home3,
    Screen.Home4,
)
