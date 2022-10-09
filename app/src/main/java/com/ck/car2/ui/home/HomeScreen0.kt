package com.ck.car2.ui.home

//import androidx.compose.material.Tab
//import androidx.compose.material.TabRowDefaults
//import androidx.compose.material.Text
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay

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
            modifier = Modifier.background(CarByComposeTheme.colors.uiBackground),
            horizontalAlignment = Alignment.CenterHorizontally,
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
//        modifier = Modifier.background(CarByComposeTheme.colors.uiBackground),
        title = {
            Text(
                text = stringResource(id = R.string.home_menu0),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
        },
    )
    /*TopAppBar(
        title = { Text(stringResource(R.string.home_menu0)) },
        backgroundColor = CarByComposeTheme.colors.uiBackground,
    )*/
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
            count = loopingCount, state = pagerState, modifier = Modifier.fillMaxWidth()
        ) { index ->
            val page = pageMapper(index)
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
            indicatorShape = RoundedCornerShape(2.dp),
            pageCount = pageCount,
            pageIndexMapping = ::pageMapper
        )

        var underDragging by remember {
            mutableStateOf(false)
        }
        LaunchedEffect(key1 = Unit) {
            pagerState.interactionSource.interactions.collect { interaction ->
                when (interaction) {
                    is PressInteraction.Press -> underDragging = true
                    is PressInteraction.Release -> underDragging = false
                    is PressInteraction.Cancel -> underDragging = false
                    is DragInteraction.Start -> underDragging = true
                    is DragInteraction.Stop -> underDragging = false
                    is DragInteraction.Cancel -> underDragging = false
                }
            }
        }
        if (underDragging.not()) {
            LaunchedEffect(key1 = underDragging) {
                try {
                    while (true) {
                        delay(3000L)
                        val current = pagerState.currentPage
                        val currentPos = pageMapper(current)
                        val nextPage = current + 1
                        if (underDragging.not()) {
                            val toPage = nextPage.takeIf { nextPage < pagerState.pageCount }
                                ?: (currentPos + startIndex + 1)
                            if (toPage > current) {
                                pagerState.animateScrollToPage(toPage)
                            } else {
                                pagerState.scrollToPage(toPage)
                            }
                        }
                    }
                } catch (e: CancellationException) {
                    Log.i("page", "Launched paging cancelled")
                }
            }
        }
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
    ScrollableTabRow(
        // Our selected tab is our current page
        selectedTabIndex = pagerState.currentPage,
        edgePadding = 0.dp,
        backgroundColor = CarByComposeTheme.colors.uiBackground,
        // Override the indicator, using the provided pagerTabIndicatorOffset modifier
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                height = 0.dp, modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        },
        divider = {
            //remove divider
        }) {
        // Add tabs for all of our pages
        hotIcons.forEachIndexed { index, hotIcon ->
            val selected = pagerState.currentPage == index
            Tab(
                selected = selected,
                text = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = hotIcon.title,
                            fontSize = 13.sp,
                            color = if (selected) CarByComposeTheme.colors.primary else CarByComposeTheme.colors.homeTabText
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .height(16.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(color = if (selected) CarByComposeTheme.colors.primary else CarByComposeTheme.colors.transparent)
                                .padding(start = 8.dp, end = 8.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "好货专区",
                                color = if (selected) CarByComposeTheme.colors.homeTabDescTextSelect else CarByComposeTheme.colors.homeTabDescTextUnSelect,
                                fontSize = 10.sp,
                                textAlign = TextAlign.Center,
                                style = LocalTextStyle.current.merge(
                                    TextStyle(
                                        platformStyle = PlatformTextStyle(
                                            includeFontPadding = false
                                        ),
                                    )
                                )
                            )
                        }

                    }
                },
                onClick = { /* TODO */ },
            )
        }
    }

    HorizontalPager(
        count = hotIcons.size,
        state = pagerState,
    ) { page ->
        Text(
            text = hotIcons[page].title.plus(hotIcons[page].id), modifier = Modifier.fillMaxHeight()
        )
    }
}
