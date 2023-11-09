package uz.gita.newsapp.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.newsapp.data.model.CategoryData
import uz.gita.newsapp.data.model.PostData
import uz.gita.newsapp.data.model.RecentData

interface AppRepository {
    fun getCategories(): Flow<Result<List<CategoryData>>>
    fun getPostsByCategory(id: Int): Flow<Result<List<PostData>>>
    fun getPostsByCategoryAndWithSearch(id: Int, text: String): Flow<Result<List<PostData>>>
    suspend fun insertRecentWordsToRoom(text: String)
    fun getAllRecent(): Flow<Result<List<RecentData>>>
}