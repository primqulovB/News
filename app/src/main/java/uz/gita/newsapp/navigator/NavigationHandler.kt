package uz.gita.newsapp.navigator

import cafe.adriel.voyager.navigator.Navigator
import kotlinx.coroutines.flow.SharedFlow

typealias navigationArgs = Navigator.() -> Unit

interface NavigationHandler {
    val uiNavigator: SharedFlow<navigationArgs>
}