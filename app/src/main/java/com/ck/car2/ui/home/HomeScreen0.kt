package com.ck.car2.ui.home

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.compose.ui.util.lerp
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavHostController
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.ck.car2.model.HotIcon
import com.ck.car2.ui.theme.CarByComposeTheme
import com.ck.car2.viewmodels.HomeUiState
import com.ck.car2.viewmodels.HomeViewModel
import com.google.accompanist.pager.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min

@Composable
fun HomeScreen0(
    homeViewModel: HomeViewModel, navController: NavHostController
) {
    val systemUiController = rememberSystemUiController()
    val uiState by homeViewModel.uiState.collectAsState()
    val bannerColorMap by homeViewModel.bannerColorMap.collectAsState()
    val hasResult = when (uiState) {
        is HomeUiState.HasPosts -> true
        is HomeUiState.NoPosts -> false
    }
    var bannerBgColor by remember { mutableStateOf(Color.Transparent) }
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    Box(
        Modifier.fillMaxSize()
    ) {
        val scroll = rememberScrollState(0)
        systemUiController.setStatusBarColor(
            color = Color.Transparent, darkIcons = scroll.value > 0
        )
        if (hasResult) {
            body(uiState = uiState, getBannerColor = { color, position ->
                homeViewModel.addBannerColor(position, color)
            }, setBannerColor = { color, position ->
                if (color != null) {
                    bannerBgColor = color
                } else if (bannerColorMap[position] != null) {
                    bannerBgColor = bannerColorMap[position]!!
                }
            }, bannerBgColor = bannerBgColor, scroll = scroll
            )
        }
        HomeTopAppBar(scroll = scroll)
        MySearchBar(
            screenWidth = screenWidth,
            bannerBgColor = bannerBgColor
        ) {
            scroll.value
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    scroll: ScrollState
) {
    val alpha = 1.0f.coerceAtMost(scroll.value / with(LocalDensity.current) { (43.dp).toPx() })
    val bgColor = CarByComposeTheme.colors.uiBackground.copy(
        alpha = alpha
    )
    val iconColor = CarByComposeTheme.colors.homeTabText.copy(
        alpha = alpha
    )

    CenterAlignedTopAppBar(
        title = {},
        modifier = Modifier
            .background(color = bgColor)
            .statusBarsPadding()
            .height(56.dp),
        navigationIcon = {
            Row(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = if (scroll.value == 0) CarByComposeTheme.colors.uiBackground else iconColor,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "武汉市洪山区",
                    modifier = Modifier.padding(start = 4.dp),
                    color = CarByComposeTheme.colors.uiBackground,
                    fontSize = 16.sp
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    tint = CarByComposeTheme.colors.uiBackground
                )
            }
        },
        actions = {
            Icon(
                modifier = Modifier
                    .padding(end = 12.dp)
                    .fillMaxHeight(),
                imageVector = Icons.Default.Notifications,
                contentDescription = null,
                tint = if (scroll.value == 0) CarByComposeTheme.colors.uiBackground else iconColor,
            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = CarByComposeTheme.colors.transparent
        )
    )
}

@Composable
fun MySearchBar(screenWidth: Dp, bannerBgColor: Color, scrollProvider: () -> Int) {
    //43dp是滑动的Y距离，30 + （56-30）/2 = 43
    val collapseRange = with(LocalDensity.current) { (43.dp).toPx() }
    val collapseFractionProvider = {
        (scrollProvider() / collapseRange).coerceIn(0f, 1f)
    }
    CollapsingSearchBar(
        modifier = Modifier.statusBarsPadding(),
        screenWidth = screenWidth,
        collapseFractionProvider = collapseFractionProvider,
    ) {
        SearchBar(bannerBgColor, scrollProvider() > 20)
    }
}

@Composable
fun CollapsingSearchBar(
    modifier: Modifier = Modifier,
    screenWidth: Dp,
    collapseFractionProvider: () -> Float,
    content: @Composable () -> Unit,
) {
    val searchBarWidthBig = screenWidth
    val searchBarWidthSmall = screenWidth - 28.dp - 28.dp
    Layout(
        modifier = modifier, content = content
    ) { measurables, constraints ->
        check(measurables.size == 1)
        val collapseFraction = collapseFractionProvider()

        val widthMaxSize = min(searchBarWidthBig.roundToPx(), constraints.maxWidth)
        val widthMinSize = max(searchBarWidthSmall.roundToPx(), constraints.minWidth)
        val imageWidth = lerp(widthMaxSize, widthMinSize, collapseFraction)
        val imagePlaceable =
            measurables[0].measure(Constraints.fixed(imageWidth, 30.dp.roundToPx()))

        val imageY = lerp(56.dp, 13.dp, collapseFraction).roundToPx()
        val imageX = lerp(
            0.dp.roundToPx(), // centered when expanded
            28.dp.roundToPx(), // right aligned when collapsed
            collapseFraction
        )
        layout(
            width = constraints.maxWidth, height = 30.dp.roundToPx()
        ) {
            imagePlaceable.placeRelative(imageX, imageY)
        }
    }
}

@Composable
fun SearchBar(bannerBgColor: Color, showGreyBg: Boolean) {
    Row(
        modifier = Modifier
            .height(30.dp)
            .padding(start = 12.dp, end = 12.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .background(if (showGreyBg) CarByComposeTheme.colors.homeTabDescTextUnSelect.copy(alpha = 0.1f) else CarByComposeTheme.colors.uiBackground),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            modifier = Modifier
                .padding(start = 8.dp, end = 6.dp)
                .size(18.dp),
            tint = CarByComposeTheme.colors.homeSearchBarTextColor
        )
        Text(
            text = "搜索您想要的商品",
            color = CarByComposeTheme.colors.homeSearchBarTextColor,
            fontSize = 12.sp,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Divider(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .height(12.dp)
                    .width(1.dp),
                color = bannerBgColor
            )
            Text(
                text = "搜索",
                color = bannerBgColor,
                fontSize = 12.sp,
            )
        }

    }
}

@Composable
fun body(
    uiState: HomeUiState,
    getBannerColor: (color: Color, position: String) -> Unit,
    setBannerColor: (color: Color?, position: String) -> Unit,
    bannerBgColor: Color,
    scroll: ScrollState
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(CarByComposeTheme.colors.uiBackground)
            .verticalScroll(scroll)
    ) {
        Column(
        ) {
            Text(
                text = "", modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(bannerBgColor, CarByComposeTheme.colors.transparent)
                        )
                    )
                    .statusBarsPadding()
                    .fillMaxWidth()
                    .height(
                        56.dp
                            .plus(30.dp)
                            .plus(148.dp)
                    )
            )

            PhotoGrid(
                (uiState as HomeUiState.HasPosts).hotIcons,
            )
            HomeViewPager(uiState.hotIcons)
        }
        Column() {
            Spacer(
                modifier = Modifier
                    .statusBarsPadding()
                    .height(
                        56.dp
                            .plus(30.dp)
                            .plus(8.dp)
                    )
            )
            Banner(uiState = uiState, getImageColor = { color, position ->
                getBannerColor(color, position)
                if (position.toInt() == 0) {
                    setBannerColor(color, position)
                }
            }, bannerItemSelected = { position: Int ->
                val size = (uiState as HomeUiState.HasPosts).hotIcons.size
                setBannerColor(
                    null, (position % size).toString()
                )
            })
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Banner(
    uiState: HomeUiState,
    getImageColor: (color: Color, position: String) -> Unit,
    bannerItemSelected: (bannerPosition: Int) -> Unit,
) {
    val pageCount = (uiState as HomeUiState.HasPosts).hotIcons.size
    val loopingCount = Int.MAX_VALUE
    val startIndex = 0
    val pagerState = rememberPagerState(initialPage = startIndex)

    LaunchedEffect(pagerState) {
        // Collect from the pager state a snapshotFlow reading the currentPage
        snapshotFlow { pagerState.currentPage }.collect { page ->
            bannerItemSelected(page)
        }
    }

    fun pageMapper(index: Int): Int {
        return (index - startIndex).floorMod(uiState.hotIcons.size)
    }

    Box() {
        HorizontalPager(
            count = loopingCount, state = pagerState, modifier = Modifier.fillMaxWidth()
        ) { index ->
            val page = pageMapper(index)
            BannerItem(
                uiState = uiState, hotIcon = uiState.hotIcons[page], getImageColor = getImageColor
            )
        }
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 8.dp, end = 20.dp),
            pageIndexMapping = ::pageMapper,
            activeColor = CarByComposeTheme.colors.homeBannerSelect,
            inactiveColor = CarByComposeTheme.colors.homeBannerUnSelect,
            indicatorWidth = 14.dp,
            indicatorHeight = 2.dp,
            spacing = 2.dp,
            indicatorShape = RoundedCornerShape(2.dp),
            pageCount = pageCount,
        )
    }

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

private fun Int.floorMod(other: Int): Int = when (other) {
    0 -> this
    else -> this - floorDiv(other) * other
}

@Composable
fun BannerItem(
    uiState: HomeUiState, hotIcon: HotIcon, getImageColor: (color: Color, index: String) -> Unit
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current).data(hotIcon.url).size(Size.ORIGINAL)
            .allowHardware(false) //IMPORTANT!
            .build()
    )
    if (painter.state is AsyncImagePainter.State.Success) {
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(140.dp)
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp)
                .clip(RoundedCornerShape(10.dp))
        )

        // This will be executed during the first composition if the image is in the memory cache.

//        if (viewModel.containsKey(hotIcon.id.toString())) {
//            return
//        }
        LaunchedEffect(key1 = hotIcon) {
            launch {
                val image = painter.imageLoader.execute(painter.request).drawable
                val bitmap = image?.toBitmap()
                if (bitmap != null) {
                    val palette = Palette.from(bitmap).generate()
                    var vibrant = palette.vibrantSwatch
                    if (vibrant == null) {
                        vibrant = palette.lightVibrantSwatch
                    }
                    val rgb = vibrant?.rgb
                    if (rgb != null) {
                        getImageColor(Color(rgb), hotIcon.id.toString())
                    } else {
                        Log.i("HomeScreen", "没有取到颜色".plus(hotIcon.id))
                    }
                }
            }
        }
    }
}


@Composable
fun PhotoGrid(hotItems: List<HotIcon>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(5),
        modifier = Modifier
            .padding(start = 12.dp, end = 12.dp)
            .height(172.dp),
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
        Spacer(modifier = Modifier.height(16.dp))
        AsyncImage(
            model = hotIcon.url,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(14.dp))
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            modifier = Modifier.height(16.dp),
            text = hotIcon.title,
            fontSize = 11.sp,
            color = CarByComposeTheme.colors.homeIconText
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeViewPager(hotIcons: List<HotIcon>) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
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
                onClick = {
                    scope.launch {
                        pagerState.scrollToPage(index)
                    }
                },

                text = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.width(64.dp),
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
            )
        }
    }

    HorizontalPager(
        count = hotIcons.size,
        state = pagerState,
    ) { page ->
        Column() {
            Text(
                text = hotIcons[page].title.plus(hotIcons[page].id),
            )
            Text(
                text = hotIcons[page].title.plus(hotIcons[page].id),
            )
            Text(
                text = hotIcons[page].title.plus(hotIcons[page].id),
            )
            Text(
                text = hotIcons[page].title.plus(hotIcons[page].id),
            )
            Text(
                text = hotIcons[page].title.plus(hotIcons[page].id),
            )
            Text(
                text = hotIcons[page].title.plus(hotIcons[page].id),
            )
            Text(
                text = hotIcons[page].title.plus(hotIcons[page].id),
            )
            Text(
                text = hotIcons[page].title.plus(hotIcons[page].id),
            )
            Text(
                text = hotIcons[page].title.plus(hotIcons[page].id),
            )
            Text(
                text = hotIcons[page].title.plus(hotIcons[page].id),
            )
            Text(
                text = hotIcons[page].title.plus(hotIcons[page].id),
            )
            Text(
                text = hotIcons[page].title.plus(hotIcons[page].id),
            )
            Text(
                text = hotIcons[page].title.plus(hotIcons[page].id),
            )
            Text(
                text = hotIcons[page].title.plus(hotIcons[page].id),
            )
            Text(
                text = hotIcons[page].title.plus(hotIcons[page].id),
            )
            Text(
                text = hotIcons[page].title.plus(hotIcons[page].id),
            )
            Text(
                text = hotIcons[page].title.plus(hotIcons[page].id),
            )
            Text(
                text = hotIcons[page].title.plus(hotIcons[page].id),
            )
            Text(
                text = hotIcons[page].title.plus(hotIcons[page].id),
            )
            Text(
                text = hotIcons[page].title.plus(hotIcons[page].id),
            )
            Text(
                text = hotIcons[page].title.plus(hotIcons[page].id),
            )
            Text(
                text = hotIcons[page].title.plus(hotIcons[page].id),
            )
            Text(
                text = hotIcons[page].title.plus(hotIcons[page].id),
            )
            Text(
                text = hotIcons[page].title.plus(hotIcons[page].id),
            )
            Text(
                text = hotIcons[page].title.plus(hotIcons[page].id),
            )
            Text(
                text = hotIcons[page].title.plus(hotIcons[page].id),
            )
            Text(
                text = hotIcons[page].title.plus(hotIcons[page].id),
            )
            Text(
                text = hotIcons[page].title.plus(hotIcons[page].id),
            )
            Text(
                text = hotIcons[page].title.plus(hotIcons[page].id),
            )
            Text(
                text = hotIcons[page].title.plus(hotIcons[page].id),
            )
            Text(
                text = hotIcons[page].title.plus(hotIcons[page].id),
            )
            Text(
                text = hotIcons[page].title.plus(hotIcons[page].id),
            )
            Text(
                text = hotIcons[page].title.plus(hotIcons[page].id),
            )
            Text(
                text = hotIcons[page].title.plus(hotIcons[page].id),
            )
            Text(
                text = hotIcons[page].title.plus(hotIcons[page].id),
            )
            Text(
                text = hotIcons[page].title.plus(hotIcons[page].id),
            )
            Text(
                text = hotIcons[page].title.plus(hotIcons[page].id),
            )
            Text(
                text = hotIcons[page].title.plus(hotIcons[page].id),
            )
        }
    }
}
