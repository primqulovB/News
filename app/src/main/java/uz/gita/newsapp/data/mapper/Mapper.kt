package uz.gita.newsapp.data.mapper

import uz.gita.newsapp.data.model.CategoryData
import uz.gita.newsapp.data.model.PostData
import uz.gita.newsapp.data.model.RecentData
import uz.gita.newsapp.data.sources.local.room.entity.RecentEntity
import uz.gita.newsapp.data.sources.remote.retrofit.response.Child
import uz.gita.newsapp.data.sources.remote.retrofit.response.PostResponseItem

fun Child.toData(): CategoryData = CategoryData(
    id = this.id,
    name = this.name
)

fun PostResponseItem.toData(): PostData = PostData(
    title = this.title,
    excerpt = this.excerpt,
    content = this.content,
    postModified = this.post_modified,
    image = this.image
)

fun RecentEntity.toData(): RecentData = RecentData(
    text = this.word
)