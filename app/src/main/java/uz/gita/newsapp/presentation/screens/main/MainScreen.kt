package uz.gita.newsapp.presentation.screens.main

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import timber.log.Timber
import uz.gita.newsapp.R
import uz.gita.newsapp.data.model.PostData
import uz.gita.newsapp.presentation.components.CategoryItem
import uz.gita.newsapp.presentation.components.CategoryShimmer
import uz.gita.newsapp.presentation.components.NewsShimmer
import uz.gita.newsapp.presentation.components.PostItem
import uz.gita.newsapp.presentation.components.PostShimmer
import uz.gita.newsapp.ui.theme.NewsAppTheme
import uz.gita.newsapp.utils.showLog
import kotlin.math.absoluteValue

class MainScreen : AndroidScreen() {
    @Composable
    override fun Content() {
        val viewModel: MainContract.ViewModel = getViewModel<MainViewModel>()

        NewsAppTheme {
            MainContent(viewModel.uiState.collectAsState().value, viewModel::onEventDispatcher)
        }
    }
}

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun MainContent(
    uiState: MainContract.UIState = MainContract.UIState(),
    onEventDispatcher: (MainContract.Intent) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {
        val systemUiController = rememberSystemUiController()
        val isSystem = isSystemInDarkTheme()

        SideEffect {
            systemUiController.setStatusBarColor(
                color = Color.Transparent,
                darkIcons = !isSystem
            )
        }

        Column {
            val isLoading = uiState.isLoading
            val swipeState = rememberSwipeRefreshState(isRefreshing = isLoading)

            SwipeRefresh(state = swipeState, onRefresh = {
                onEventDispatcher.invoke(MainContract.Intent.Refresh)
            }) {
                LazyColumn {
                    if (!uiState.isConnection) {
                        items(12) {
                            Box(
                                modifier = Modifier
                                    .height(56.dp)
                                    .fillMaxWidth()
                            )
                        }
                    } else {
                        item {




                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 15.dp),
                            ) {


                                var active by remember { mutableStateOf(false) }
                                val searchText = uiState.searchText


                                SearchBar(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .heightIn(0.dp, 280.dp)
                                        .wrapContentHeight()
                                        .align(Alignment.CenterVertically),
                                    query = searchText,
                                    onQueryChange = {
                                        if (it.length < 15) {
                                            onEventDispatcher.invoke(
                                                MainContract.Intent.SetSearchText(
                                                    it
                                                )
                                            )
                                        }
                                    },
                                    onSearch = {
                                        active = false

                                        if (uiState.postData.isNotEmpty() && uiState.categoryData.isNotEmpty()) {
                                            onEventDispatcher.invoke(
                                                MainContract.Intent.InsertRecent(
                                                    searchText
                                                )
                                            )

                                            onEventDispatcher.invoke(
                                                MainContract.Intent.ClickSearch(
                                                    text = searchText,
                                                    id = if (uiState.selectedCategory.id == 0) 3 else uiState.selectedCategory.id
                                                )
                                            )
                                        }
                                    },
                                    active = active,
                                    onActiveChange = {
                                        active = it
                                    },
                                    leadingIcon = {
                                        if (!active) {
                                            Icon(
                                                imageVector = Icons.Default.Search,
                                                contentDescription = "search"
                                            )
                                        } else {
                                            Icon(
                                                modifier = Modifier
                                                    .clickable { active = false },
                                                imageVector = Icons.Default.ArrowBack,
                                                contentDescription = "back"
                                            )
                                        }
                                    },
                                    trailingIcon = {
                                        if (active) {
                                            Icon(
                                                modifier = Modifier
                                                    .clickable {
                                                        onEventDispatcher.invoke(
                                                            MainContract.Intent.SetSearchText(
                                                                ""
                                                            )
                                                        )
                                                    },
                                                imageVector = Icons.Default.Close,
                                                contentDescription = "close"
                                            )
                                        }
                                    },
                                    placeholder = {
                                        Text(text = "Qidiruv")
                                    }
                                ) {





                                    uiState.recentWords.forEach {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable {
                                                    active = false

                                                    onEventDispatcher.invoke(
                                                        MainContract.Intent.ClickSearch(
                                                            text = it.text,
                                                            id = if (uiState.selectedCategory.id == 0) 3 else uiState.selectedCategory.id
                                                        )
                                                    )
                                                }
                                                .padding(all = 14.dp)
                                        ) {
                                            Icon(
                                                modifier = Modifier.padding(end = 10.dp),
                                                imageVector = Icons.Default.History,
                                                contentDescription = "History"
                                            )

                                            Text(
                                                text = it.text
                                            )
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))























                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 15.dp),
                                text = "So'nggi xabarlar",
                                fontSize = 18.sp,
                                lineHeight = 20.8.sp,
                                maxLines = 1,
                                color = MaterialTheme.colors.onPrimary,
                                fontFamily = FontFamily(Font(R.font.new_york_medium_medium)),
                                fontWeight = FontWeight(700),
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            if (uiState.newsData.isNotEmpty() || uiState.searchText.isNotEmpty()) {
                                ViewPagerSlider(uiState.newsData) {
                                    onEventDispatcher.invoke(MainContract.Intent.ClickPost(it))
                                }
                            } else {
                                NewsShimmer()
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            if (uiState.categoryData.isNotEmpty() || uiState.searchText.isNotEmpty()) {
                                val categoryData = uiState.categoryData
                                var name by remember { mutableStateOf(uiState.selectedCategory.name) }
                                var job by remember { mutableStateOf<Job?>(null) }

                                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    items(uiState.categoryData.size) { index ->
                                        if (index == 0) {
                                            Spacer(modifier = Modifier.width(15.dp))
                                        }

                                        CategoryItem(
                                            categoryData[index].name,
                                            name == uiState.categoryData[index].name
                                        ) {
                                            job?.cancel()
                                            job = CoroutineScope(Dispatchers.Default).launch {
                                                delay(500)

                                                onEventDispatcher.invoke(
                                                    MainContract.Intent.ClickCategory(categoryData[index].id)
                                                )
                                            }

                                            name = uiState.categoryData[index].name
                                            onEventDispatcher.invoke(
                                                MainContract.Intent.SetSelectedCategory(
                                                    categoryData[index]
                                                )
                                            )
                                        }

                                        if (index == categoryData.size - 1) {
                                            Spacer(modifier = Modifier.width(15.dp))
                                        }
                                    }
                                }
                            } else {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Spacer(modifier = Modifier.width(7.dp))

                                    repeat(7) {
                                        CategoryShimmer()
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        val postData = uiState.postData

                        if (uiState.postData.isNotEmpty()) {
                            items(uiState.postData.size) { index ->
                                if (index != 0) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                }

                                PostItem(
                                    postData[index].image,
                                    postData[index].title,
                                    postData[index].postModified
                                ) {
                                    onEventDispatcher.invoke(MainContract.Intent.ClickPost(postData[index]))
                                }
                            }
                        } else {
                            item {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    if ((uiState.postData.isEmpty() && uiState.searchText.isNotEmpty() && !uiState.isLoading) || (uiState.postData.isEmpty() && uiState.categoryData.isEmpty() && !uiState.isLoading)) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(300.dp)
                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .align(Alignment.Center)
                                            ) {
                                                Image(
                                                    modifier = Modifier
                                                        .size(200.dp)
                                                        .align(Alignment.CenterHorizontally),
                                                    painter = painterResource(id = R.drawable.img_3),
                                                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary),
                                                    contentDescription = "placeHolder"
                                                )

                                                Text(
                                                    text = "So'rov bo'yicha ma'lumot topilmadi!",
                                                    textAlign = TextAlign.Center,
                                                    color = MaterialTheme.colors.onPrimary,
                                                    fontWeight = FontWeight(500),
                                                    fontSize = 20.sp
                                                )
                                            }
                                        }
                                    } else {
                                        repeat(3) {
                                            PostShimmer()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (!uiState.isConnection) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
            ) {
                Image(
                    modifier = Modifier
                        .size(200.dp)
                        .padding(start = 30.dp),
                    painter = painterResource(id = R.drawable.img1),
                    contentDescription = "placeHolder"
                )

                Text(
                    text = "Server bilan muammo!\niltimos yangilang",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onPrimary,
                    fontWeight = FontWeight(500),
                    fontSize = 20.sp
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@ExperimentalPagerApi
@Composable
private fun ViewPagerSlider(
    postData: List<PostData>,
    onClick: (PostData) -> Unit
) {
    val pagerState = rememberPagerState(
        pageCount = postData.size,
        initialPage = 0
    )

    LaunchedEffect(Unit) {
        while (true) {
            yield()
            delay(2000)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % (pagerState.pageCount),
                animationSpec = tween(1000)
            )
        }
    }

    Box(
        modifier = Modifier
            .height(240.dp)
            .fillMaxWidth()
    ) {
        HorizontalPager(
            dragEnabled = false,
            state = pagerState,
            modifier = Modifier
                .fillMaxSize(),
        ) { page ->
            Card(
                modifier = Modifier
                    .size(330.dp)
                    .fillMaxSize()
                    .graphicsLayer {
                        val pageOffset =
                            ((pagerState.currentPage - page) + pagerState.currentPageOffset).absoluteValue

                        lerp(
                            start = 0.95f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }

                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
                    .clickable { onClick.invoke(postData[page]) },
                shape = RoundedCornerShape(8.dp)
            ) {
                val data = postData[page]

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    GlideImage(
                        model = data.image,
                        contentDescription = "image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black
                                    )
                                ),
                                alpha = 0.8f
                            )
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp, top = 80.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                            text = data.title,
                            fontSize = 16.sp,
                            color = Color.White,
                            maxLines = 3,
                            lineHeight = 20.8.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_medium)),
                            fontWeight = FontWeight(700),
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp, top = 8.dp),
                            text = data.excerpt,
                            fontSize = 10.sp,
                            maxLines = 2,
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.nunito_medium)),
                            fontWeight = FontWeight(400),
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview() {
    NewsAppTheme {
        MainContent()
    }
}