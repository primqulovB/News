package uz.gita.newsapp.navigator

import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationDispatcher @Inject constructor() : AppNavigator, NavigationHandler {
    override val uiNavigator = MutableSharedFlow<navigationArgs>()

    private suspend fun navigateTo(navArgs: navigationArgs) {
        uiNavigator.emit(navArgs)
    }

    override suspend fun openScreenWithSave(screen: myScreen) = navigateTo {
        this.push(screen)
    }

    override suspend fun openScreenWithoutSave(screen: myScreen) = navigateTo {
        this.replace(screen)
    }

    override suspend fun backToUntil(screen: myScreen) = navigateTo {
        this.popUntil { it == screen }
    }

    override suspend fun back() = navigateTo {
        this.pop()
    }

    override suspend fun backToRoot() = navigateTo {
        this.popUntilRoot()
    }
}