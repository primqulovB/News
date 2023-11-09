package uz.gita.newsapp.presentation.screens.detail

import uz.gita.newsapp.navigator.AppNavigator
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailDirection @Inject constructor(
    private val appNavigator: AppNavigator
) : DetailContract.Direction {
    override suspend fun back() {
        appNavigator.back()
    }
}