package uz.gita.newsapp.data.sources.remote.retrofit.response

data class CategoryResponseItem(
    val child: List<Child>,
    val id: Int,
    val name: String,
    val slug: String
)