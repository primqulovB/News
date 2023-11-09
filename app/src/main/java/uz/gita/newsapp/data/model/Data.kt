package uz.gita.newsapp.data.model

data class PostData(
    val title: String,
    val excerpt: String,
    val content: String,
    val postModified: String,
    val image: String
)

data class CategoryData(
    val id: Int,
    val name: String
)

data class RecentData(
    val text: String
)