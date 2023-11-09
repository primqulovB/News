package uz.gita.newsapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import uz.gita.newsapp.R
import uz.gita.newsapp.ui.theme.NewsAppTheme

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PostItem(
    imageUrl: String = "",
    title: String = "",
    date: String = "",
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .height(128.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Transparent, RoundedCornerShape(8.dp))
            .clickable { onClick.invoke() }
    ) {
        GlideImage(
            modifier = Modifier
                .fillMaxSize(),
            model = imageUrl,
            loading = placeholder(R.drawable.img),
            contentDescription = "image",
            contentScale = ContentScale.Crop
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
                    alpha = 0.9f
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Text(
                text = title,
                maxLines = 2,
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 20.8.sp,
                    fontFamily = FontFamily(Font(R.font.new_york_medium_medium)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFFFFFFFF),
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = date,
                    maxLines = 1,
                    style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 20.8.sp,
                        fontFamily = FontFamily(Font(R.font.nunito_medium)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFFFFFFFF),
                    )
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview() {
    NewsAppTheme {
        PostItem {}
    }
}