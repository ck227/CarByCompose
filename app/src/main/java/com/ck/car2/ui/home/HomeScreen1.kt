package com.ck.car2.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.ck.car2.model.HotIcon
import com.ck.car2.ui.theme.CarByComposeTheme
import com.ck.car2.ui.theme.color_light_green
import com.ck.car2.viewmodels.HomeUiState
import com.ck.car2.viewmodels.HomeViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun HomeScreen1(
    homeViewModel: HomeViewModel,
    navController: NavHostController
) {
    val systemUiController = rememberSystemUiController()
    val uiState by homeViewModel.uiState.collectAsState()
    val hasResult = when (uiState) {
        is HomeUiState.HasPosts -> true
        is HomeUiState.NoPosts -> false
    }

    systemUiController.setStatusBarColor(
        color = Color.Transparent, darkIcons = true
    )
    Column() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = color_light_green)
                .statusBarsPadding()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, top = 6.dp, end = 12.dp, bottom = 6.dp)
                    .height(30.dp)
                    .border(
                        width = 1.dp,
                        color = CarByComposeTheme.colors.primary,
                        shape = RoundedCornerShape(15.dp)
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 16.dp, end = 6.dp)
                        .size(18.dp),
                    tint = CarByComposeTheme.colors.homeSearchBarTextColor
                )
                Text(
                    text = "搜索您想要的商品",
                    color = CarByComposeTheme.colors.homeSearchBarTextColor,
                    fontSize = 12.sp,
                )
            }
            if (hasResult) {
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                ) {
                    (uiState as HomeUiState.HasPosts).hotIcons.forEach { message ->
                        TitleRowItem(message, gridItemSelected = { gridItem ->

                        })
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        Row {
            RowLeft()
        }
    }
}

@Composable
fun TitleRowItem(hotIcon: HotIcon, gridItemSelected: (photoId: Int) -> Unit) {
    Column(
        modifier = Modifier.padding(start = 12.dp, end = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(model = hotIcon.url,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(12.dp))
                .clickable {
                    gridItemSelected(hotIcon.id)
                })
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = hotIcon.title,
            fontSize = 11.sp,
            color = CarByComposeTheme.colors.homeIconText
        )
    }
}

@Composable
fun RowLeft() {
    Column(
        modifier = Modifier.width(120.dp)
    ) {
        repeat(100) {
            Text(
                text = "分类",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(color = CarByComposeTheme.colors.homeTabDescTextUnSelect),
                textAlign = TextAlign.Center,

            )
        }
    }


}
