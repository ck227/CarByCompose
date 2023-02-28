package com.ck.car2.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.ck.car2.model.HotIcon
import com.ck.car2.ui.theme.*
import com.ck.car2.viewmodels.HomeUiState
import com.ck.car2.viewmodels.HomeViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun HomeScreen1() {
    /*val systemUiController = rememberSystemUiController()
    val uiState by homeViewModel.uiState.collectAsState()
    val selectTypePosition by homeViewModel.selectTypePosition.collectAsState()
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
                    ), verticalAlignment = Alignment.CenterVertically
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
                    modifier = Modifier.horizontalScroll(rememberScrollState())
                ) {
                    (uiState as HomeUiState.HasPosts).hotIcons.forEachIndexed { index, hotIcon ->
                        TitleRowItem(index = index,
                            selectTypePosition = selectTypePosition,
                            hotIcon = hotIcon,
                            gridItemSelected = { selectIndex ->
                                homeViewModel.updateTypeSelectIndex(selectIndex)
                            })
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        Row {
            RowLeft((uiState as HomeUiState.HasPosts).hotIcons)
            RowRight((uiState as HomeUiState.HasPosts).hotIcons)
        }
    }*/
}

@Composable
fun TitleRowItem(
    index: Int, selectTypePosition: Int, hotIcon: HotIcon, gridItemSelected: (photoId: Int) -> Unit
) {
    Column(
        modifier = Modifier.padding(start = 12.dp, end = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(model = hotIcon.url,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(40.dp)
                .border(
                    width = 2.dp,
                    shape = RoundedCornerShape(12.dp),
                    color = if (index == selectTypePosition) CarByComposeTheme.colors.primary else Color.Transparent,
                )
                .padding(2.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                    gridItemSelected(index)
                })
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = hotIcon.title,
            fontSize = 11.sp,
            color = CarByComposeTheme.colors.homeCategoryTitleUnSelect
        )
    }
}

@Composable
fun RowLeft(hotIcons: List<HotIcon>) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .fillMaxHeight()
            .background(CarByComposeTheme.colors.homeCategoryUnSelect)
            .verticalScroll(rememberScrollState())
    ) {
        hotIcons.forEachIndexed { index, hotIcon ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(CarByComposeTheme.colors.homeCategoryUnSelect),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "荐",
                    modifier = Modifier
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(color_dark_red, color_light_red)
                            ),
                            shape = RoundedCornerShape(
                                topStart = 4.dp, bottomEnd = 4.dp
                            ),
                        )
                        .padding(start = 3.dp, end = 3.dp, top = 1.dp, bottom = 1.dp),
                    color = CarByComposeTheme.colors.homeCategoryUnSelect,
                    fontSize = 9.sp,
                    style = LocalTextStyle.current.merge(
                        TextStyle(
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            ),
                        )
                    ),
                )
                Text(
                    text = hotIcon.title,
                    modifier = Modifier.padding(start = 2.dp),
                    color = CarByComposeTheme.colors.homeCategoryLeftUnSelect,
                    fontSize = 11.sp,
                    style = LocalTextStyle.current.merge(
                        TextStyle(
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            ),
                        )
                    ),
                )
            }
        }
    }
}

@Composable
fun RowRight(hotIcons: List<HotIcon>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        hotIcons.forEachIndexed { index, hotIcon ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {

                    }
                    .padding(
                        start = 12.dp, top = 16.dp, end = 12.dp, bottom = 16.dp
                    )
            ) {
                AsyncImage(
                    model = hotIcon.url,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(20))
                )
                Column(
                    modifier = Modifier.padding(start = 12.dp)
                ) {
                    Text(text = hotIcon.title, fontSize = 13.sp, color = color_grey333)
                }
            }
            Divider(
                thickness = 0.5.dp,
                color = color_divider,
                modifier = Modifier.padding(start = 12.dp, end = 12.dp)
            )
        }
    }
}
