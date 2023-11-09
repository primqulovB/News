package uz.gita.newsapp.presentation.screens.main

import uz.gita.newsapp.data.model.PostData
import uz.gita.newsapp.navigator.AppNavigator
import uz.gita.newsapp.presentation.screens.detail.DetailScreen
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainDirection @Inject constructor(
    private val appNavigator: AppNavigator
) : MainContract.Direction {
    override suspend fun openDetailScreen(data: PostData) {
        appNavigator.openScreenWithSave(DetailScreen(data))
    }
}