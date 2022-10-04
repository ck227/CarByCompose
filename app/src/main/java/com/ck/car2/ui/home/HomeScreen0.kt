package com.ck.car2.ui.home

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ck.car2.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen0(
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            HomeTopAppBar()
        }
    ) { it ->
        Text(
            text = "home0啊啊啊",
            modifier = Modifier.padding(it)
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.home_menu0),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        },
    )
}