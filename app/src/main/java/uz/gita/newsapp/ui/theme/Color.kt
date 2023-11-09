package uz.gita.newsapp.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

sealed class ThemeColor(
    val background: Color,
    val text: Color,
    val bg: Color
) {
    object Night : ThemeColor(
        background = Color(0xFF1B1B1F),
        text = Color(0xFFffffff),
        bg = Color(0xFF404047)
    )

    object Day : ThemeColor(
        background = Color(0xFFffffff),
        text = Color(0xFF000000),
        bg = Color(0xFFCCCCCC)
    )
}