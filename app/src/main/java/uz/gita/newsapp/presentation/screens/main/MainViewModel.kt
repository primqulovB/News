package uz.gita.newsapp.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.gita.newsapp.data.model.CategoryData
import uz.gita.newsapp.domain.repository.AppRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val direction: MainContract.Direction
) : MainContract.ViewModel, ViewModel() {
    override val uiState = MutableStateFlow(MainContract.UIState())

    private fun load() {
        uiState.update {
            it.copy(
                isLoading = true,
                isConnection = true
            )
        }

        appRepository.getCategories()
            .onEach {
                it.onSuccess { data ->
                    uiState.update {
                        it.copy(
                            categoryData = data
                        )
                    }
                }

                it.onFailure {
                    uiState.update { it.copy(isConnection = false) }
                }
            }
            .onCompletion { uiState.update { it.copy(isLoading = false) } }
            .launchIn(viewModelScope)

        appRepository.getPostsByCategory(uiState.value.selectedCategory.id)
            .onEach {
                it.onSuccess { data ->
                    uiState.update {
                        it.copy(
                            postData = data,
                            newsData = data,
                        )
                    }
                }

                it.onFailure {
                    uiState.update { it.copy(isConnection = false) }
                }
            }
            .onCompletion { uiState.update { it.copy(isLoading = false) } }
            .launchIn(viewModelScope)

        appRepository.getAllRecent()
            .onEach {
                it.onSuccess { data ->
                    uiState.update { it.copy(recentWords = data) }
                }

                it.onFailure {

                }
            }
            .launchIn(viewModelScope)
    }

    init {
        load()
    }

    override fun onEventDispatcher(intent: MainContract.Intent) {
        when (intent) {
            is MainContract.Intent.ClickCategory -> {
                appRepository.getPostsByCategoryAndWithSearch(intent.id, uiState.value.searchText)
                    .onStart {
                        uiState.update {
                            it.copy(
                                isConnection = true,
                                postData = listOf(),
                                isLoading = true
                            )
                        }
                    }
                    .onEach {
                        it.onSuccess { data ->
                            uiState.update { it.copy(postData = data, isLoading = false) }
                        }

                        it.onFailure {
                            uiState.update { it.copy(isConnection = false, isLoading = false) }
                        }
                    }
                    .launchIn(viewModelScope)
            }

            is MainContract.Intent.InsertRecent -> {
                viewModelScope.launch {
                    appRepository.insertRecentWordsToRoom(intent.text)
                }
            }

            is MainContract.Intent.ClickSearch -> {
                uiState.update { it.copy(postData = listOf(), searchText = intent.text, isConnection = true, isLoading = true) }

                appRepository.getPostsByCategoryAndWithSearch(intent.id, intent.text)
                    .onEach {
                        it.onSuccess { data ->
                            uiState.update { it.copy(postData = data) }
                        }

                        it.onFailure {
                            uiState.update { it.copy(isConnection = false) }
                        }
                    }
                    .onCompletion { uiState.update { it.copy(isLoading = false) } }
                    .launchIn(viewModelScope)

                appRepository.getAllRecent()
                    .onEach {
                        it.onSuccess { data ->
                            uiState.update { it.copy(recentWords = data) }
                        }

                        it.onFailure {

                        }
                    }
                    .launchIn(viewModelScope)
            }

            is MainContract.Intent.ClickPost -> {
                viewModelScope.launch {
                    direction.openDetailScreen(intent.data)
                }
            }

            is MainContract.Intent.SetSearchText -> {
                uiState.update { it.copy(searchText = intent.text) }
            }

            MainContract.Intent.Refresh -> {
                uiState.update {
                    it.copy(
                        postData = listOf(),
                        categoryData = listOf(),
                        newsData = listOf(),
                        isLoading = true,
                        isConnection = true,
                        searchText = "",
                        selectedCategory = CategoryData(3, "Телефон")
                    )
                }

                appRepository.getCategories()
                    .onEach {
                        it.onSuccess { data ->
                            uiState.update {
                                it.copy(
                                    categoryData = data,
                                    isLoading = false
                                )
                            }
                        }

                        it.onFailure {
                            uiState.update { it.copy(isConnection = false, isLoading = false) }
                        }
                    }
                    .launchIn(viewModelScope)

                appRepository.getPostsByCategory(uiState.value.selectedCategory.id)
                    .onEach {
                        it.onSuccess { data ->
                            uiState.update {
                                it.copy(
                                    postData = data,
                                    newsData = data,
                                    isLoading = false
                                )
                            }
                        }

                        it.onFailure {
                            uiState.update { it.copy(isConnection = false, isLoading = false) }
                        }
                    }
                    .launchIn(viewModelScope)
            }

            is MainContract.Intent.SetSelectedCategory -> {
                uiState.update { it.copy(selectedCategory = intent.data) }
            }
        }
    }
}