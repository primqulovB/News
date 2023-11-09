package uz.gita.newsapp.presentation.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.newsapp.domain.repository.AppRepository
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val direction: DetailContract.Direction
) : DetailContract.ViewModel, ViewModel() {
    override fun onEventDispatcher(intent: DetailContract.Intent) {
        when (intent) {
            DetailContract.Intent.ClickBack -> {
                viewModelScope.launch {
                    direction.back()
                }
            }
        }
    }
}