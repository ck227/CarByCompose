package com.ck.car2.ui.detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DetailScreen(productId: String) {
    Text(text = "详情页面".plus(productId))
}