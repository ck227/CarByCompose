package com.ck.car2.ui.home


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import coil.compose.AsyncImagePainter
import coil.transform.CircleCropTransformation
import coil.transform.Transformation
import com.ck.car2.R
import com.ck.car2.model.HotIcon
import com.ck.car2.viewmodels.HomeUiState
import com.ck.car2.viewmodels.HomeViewModel

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
//        .associate { hotIcon ->
//        key(hotIcon.id) {
//            hotIcon.id to rememberLazyListState()
//        }
//    }
    Scaffold(topBar = {
        HomeTopAppBar()
    }) { it ->
        if (hasResult) {
            PhotoGrid((uiState as HomeUiState.HasPosts).hotIcons, modifier = Modifier.padding(it))
        }
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

@Composable
fun PhotoGrid(hotItems: List<HotIcon>, modifier: Modifier) {
    LazyVerticalGrid(
        horizontalArrangement = Arrangement.SpaceAround,
        columns = GridCells.Fixed(5),
        modifier = modifier
    ) {
        items(hotItems) {
            PhotoItem(it)
        }
    }
}

@Composable
fun PhotoItem(hotIcon: HotIcon) {
    Column(

    ) {
        AsyncImage(
            model = hotIcon.url,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = hotIcon.title, fontSize = 11.sp)
    }

}
