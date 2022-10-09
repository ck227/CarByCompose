package com.ck.car2.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.ck.car2.R
import com.ck.car2.model.HotIcon
import com.ck.car2.ui.theme.CarByComposeTheme
import com.ck.car2.viewmodels.HomeUiState
import com.ck.car2.viewmodels.HomeViewModel
import com.google.accompanist.pager.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen0(
    homeViewModel: HomeViewModel, navController: NavHostController
) {
    val uiState by homeViewModel.uiState.collectAsState()
    val hasResult = when (uiState) {
        is HomeUiState.HasPosts -> true
        is HomeUiState.NoPosts -> false
    }
    Scaffold(topBar = {
        HomeTopAppBar()
    }) { it ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (hasResult) {
                Banner(
                    (uiState as HomeUiState.HasPosts).hotIcons, modifier = Modifier.padding(it)
                )
                PhotoGrid(
                    (uiState as HomeUiState.HasPosts).hotIcons
                )
                HomeViewPager((uiState as HomeUiState.HasPosts).hotIcons)
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar() {
    CenterAlignedTopAppBar(
//        modifier = Modifier.height(76.dp),
        title = {
            Text(
                text = stringResource(id = R.string.home_menu0),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
        },
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Banner(hotItems: List<HotIcon>, modifier: Modifier) {
    val pageCount = hotItems.size
    val loopingCount = Int.MAX_VALUE
    val startIndex = 0
    val pagerState = rememberPagerState(initialPage = startIndex)

    fun pageMapper(index: Int): Int {
        return (index - startIndex).floorMod(hotItems.size)
    }

    Box(modifier = modifier) {
        HorizontalPager(
            count = hotItems.size,
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { index ->
            val page = (index - startIndex).floorMod(hotItems.size)
            BannerItem(hotItems[page])
        }



        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 8.dp, end = 20.dp),
            indicatorWidth = 14.dp,
            indicatorHeight = 2.dp,
            spacing = 2.dp,
            indicatorShape = RoundedCornerShape(2.dp)
        )
    }
}

private fun Int.floorMod(other: Int): Int = when (other) {
    0 -> this
    else -> this - floorDiv(other) * other
}

@Composable
fun BannerItem(hotIcon: HotIcon) {
    AsyncImage(
        model = hotIcon.url,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .height(120.dp)
            .padding(start = 12.dp, end = 12.dp)
            .clip(RoundedCornerShape(14.dp))
    )
}

@Composable
fun PhotoGrid(hotItems: List<HotIcon>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(5),
        modifier = Modifier.padding(start = 12.dp, end = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        userScrollEnabled = false
    ) {
        items(hotItems) {
            PhotoItem(it)
        }
    }
}

@Composable
fun PhotoItem(hotIcon: HotIcon) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        AsyncImage(
            model = hotIcon.url,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = hotIcon.title, fontSize = 11.sp, color = CarByComposeTheme.colors.homeIconText)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeViewPager(hotIcons: List<HotIcon>) {
    val pagerState = rememberPagerState()
    TabRow(
        // Our selected tab is our current page
        selectedTabIndex = pagerState.currentPage,
        // Override the indicator, using the provided pagerTabIndicatorOffset modifier
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        }
    ) {
        // Add tabs for all of our pages
        hotIcons.forEachIndexed { index, hotIcon ->
            Tab(
                text = {
                    Text(text = hotIcon.title)
                },
                selected = pagerState.currentPage == index,
                onClick = { /* TODO */ },
            )
        }
    }

    HorizontalPager(
        count = hotIcons.size,
        state = pagerState,
    ) { page ->
        // TODO: page content
        Text(text = hotIcons[page].title.plus(hotIcons[page].id))

    }
}
