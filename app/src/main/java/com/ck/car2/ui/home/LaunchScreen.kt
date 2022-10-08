package com.ck.car2.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.ck.car2.R
import kotlinx.coroutines.delay

@Composable
fun LaunchScreen(onTimeOut: () -> Unit) {
    var ticks by remember { mutableStateOf(1) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            ticks--
            if (ticks == 0) {
                onTimeOut()
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null
        )
    }
}