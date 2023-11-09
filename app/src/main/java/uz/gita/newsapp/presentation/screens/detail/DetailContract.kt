package uz.gita.newsapp.presentation.screens.detail

interface DetailContract {
    interface ViewModel {
        fun onEventDispatcher(intent: Intent)
    }

    interface Intent {
        object ClickBack : Intent
    }

    interface Direction {
        suspend fun back()
    }
}