package com.ck.car2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.ck.car2.graphs.RootNavigationGraph
import com.ck.car2.ui.theme.CarByComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //处理通知栏
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            CarByComposeTheme {
                RootNavigationGraph(
                    navController = rememberNavController()
                )
            }
        }
    }
}
