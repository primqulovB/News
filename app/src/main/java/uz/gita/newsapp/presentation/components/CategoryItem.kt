package uz.gita.newsapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.gita.newsapp.ui.theme.NewsAppTheme

@Composable
fun CategoryItem(
    text: String = "",
    isClick: Boolean = false,
    onCLick: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(32.dp)
            .background(
                color = if (isClick) MaterialTheme.colors.onPrimary else MaterialTheme.colors.background,
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .border(
                0.5.dp,
                if (isClick) Color(0xFFFFFFFF) else Color(0xFFF0F1FA),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onCLick.invoke() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            color = if (isClick) MaterialTheme.colors.background else MaterialTheme.colors.onPrimary,
            text = text,
            fontSize = 12.sp,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview() {
    NewsAppTheme {
        CategoryItem {}
    }
}