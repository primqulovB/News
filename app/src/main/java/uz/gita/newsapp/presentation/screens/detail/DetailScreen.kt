package uz.gita.newsapp.presentation.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.parseAsHtml
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import uz.gita.newsapp.R
import uz.gita.newsapp.data.model.PostData
import uz.gita.newsapp.ui.theme.NewsAppTheme

class DetailScreen(
    private val data: PostData
) : AndroidScreen() {
    @Composable
    override fun Content() {
        val viewModel: DetailContract.ViewModel = getViewModel<DetailViewModel>()

        NewsAppTheme {
            MainContent(
                data,
                viewModel::onEventDispatcher
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun MainContent(
    data: PostData = PostData("", "", "", "", ""),
    onEventDispatcher: (DetailContract.Intent) -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val systemUiController = rememberSystemUiController()
        val isSystem = isSystemInDarkTheme()

        SideEffect {
            systemUiController.setStatusBarColor(
                color = Color.Transparent,
                darkIcons = !isSystem
            )
        }

        LazyColumn {
            item {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column {
                        GlideImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp),
                            contentScale = ContentScale.Crop,
                            model = data.image,
                            contentDescription = "image"
                        )

                        Column(
                            modifier = Modifier
                                .offset(y = (-20).dp)
                                .background(
                                    color = MaterialTheme.colors.background,
                                    shape = RoundedCornerShape(24.dp, 24.dp, 0.dp, 0.dp)
                                )
                                .padding(start = 15.dp, end = 15.dp, top = 88.dp, bottom = 0.dp)
                        ) {
                            Text(
                                text = data.content.parseAsHtml().toString(),
                                color = MaterialTheme.colors.onPrimary
                            )
                        }
                    }

                    Card(
                        modifier = Modifier
                            .height(46.dp)
                            .width(54.dp)
                            .align(Alignment.TopStart)
                            .clickable { onEventDispatcher.invoke(DetailContract.Intent.ClickBack) }
                            .padding(start = 15.dp, top = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colors.primary,
                        ),
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Icon(
                                modifier = Modifier.align(Alignment.Center),
                                imageVector = Icons.Default.ArrowBackIosNew,
                                contentDescription = "back",
                                tint = MaterialTheme.colors.onPrimary
                            )
                        }
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(450.dp)
                            .align(Alignment.TopCenter)
                            .padding(start = 32.dp, end = 32.dp, top = 300.dp, bottom = 0.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colors.primary
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 24.dp, vertical = 16.dp)
                        ) {
                            Text(
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    lineHeight = 20.8.sp,
                                    fontFamily = FontFamily(Font(R.font.nunito_medium)),
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colors.onPrimary,
                                ),
                                text = data.postModified
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = data.title,
                                maxLines = 3,
                                color = MaterialTheme.colors.onPrimary
                            )
                        }
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