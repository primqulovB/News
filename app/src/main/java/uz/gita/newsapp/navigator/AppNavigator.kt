package uz.gita.newsapp.navigator

import cafe.adriel.voyager.androidx.AndroidScreen

typealias myScreen = AndroidScreen

interface AppNavigator {
    suspend fun openScreenWithSave(screen: myScreen)
    suspend fun openScreenWithoutSave(screen: myScreen)
    suspend fun backToUntil(screen: myScreen)
    suspend fun back()
    suspend fun backToRoot()
}