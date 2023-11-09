package uz.gita.newsapp.presentation.screens.main

import kotlinx.coroutines.flow.StateFlow
import uz.gita.newsapp.data.model.CategoryData
import uz.gita.newsapp.data.model.PostData
import uz.gita.newsapp.data.model.RecentData

interface MainContract {
    interface ViewModel {
        val uiState: StateFlow<UIState>

        fun onEventDispatcher(intent: Intent)
    }

    interface Intent {
        data class ClickCategory(
            val id: Int
        ) : Intent

        data class ClickPost(
            val data: PostData
        ) : Intent

        data class ClickSearch(
            val text: String,
            val id: Int
        ) : Intent

        object Refresh: Intent

        data class SetSearchText(
            val text: String
        ) : Intent

        data class SetSelectedCategory(
            val data: CategoryData
        ) : Intent

        data class InsertRecent(
            val text: String
        ) : Intent
    }

    interface Direction {
        suspend fun openDetailScreen(data: PostData)
    }

    data class UIState(
        val isLoading: Boolean = false,
        var categoryData: List<CategoryData> = listOf(),
        val postData: List<PostData> = listOf(),
        val newsData: List<PostData> = listOf(),
        val recentWords: List<RecentData> = listOf(),
        val selectedCategory: CategoryData = CategoryData(3, "Телефон"),
        val searchText: String = "",
        val isConnection: Boolean = true
    )
}