package com.ck.car2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.ck.car2.graphs.RootNavigationGraph
import com.ck.car2.ui.theme.CarByComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //处理通知栏
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val appContainer = (application as CarByComposeApplication).container
        setContent {
            CarByComposeTheme {
                /*Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    RootNavigationGraph(
                        appContainer = appContainer,
                        navController = rememberNavController()
                    )
                }*/
                RootNavigationGraph(
                    appContainer = appContainer,
                    navController = rememberNavController()
                )
            }
        }
    }
}
