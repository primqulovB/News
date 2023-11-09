package uz.gita.newsapp.data.sources.remote.retrofit.response

data class PostResponseItem(
    val category_id: Int,
    val category_name: String,
    val content: String,
    val excerpt: String,
    val id: Int,
    val image: String,
    val order: String,
    val post_id: String,
    val post_modified: String,
    val priority: String,
    val published_at: Int,
    val title: String,
    val updated_at: Int,
    val url: String
)