package com.ck.car2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.ck.car2.graphs.RootNavigationGraph
import com.ck.car2.ui.theme.CarByComposeTheme

/**
 * 启动页面/主页的graphs
 * 自定义主题颜色
 * 添加网络请求
 *
 *
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val appContainer = (application as CarByComposeApplication).container
        setContent {
            CarByComposeTheme {
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    RootNavigationGraph(
                        appContainer = appContainer, navController = navController
                    )
                }
            }
        }
    }
}
