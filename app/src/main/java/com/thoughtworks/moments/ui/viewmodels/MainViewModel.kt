package com.thoughtworks.moments.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thoughtworks.moments.domain.entity.Tweet
import com.thoughtworks.moments.domain.entity.User
import com.thoughtworks.moments.domain.repository.MomentRepository
import com.thoughtworks.moments.domain.useCase.GetInitialTweetsUseCase
import com.thoughtworks.moments.domain.useCase.LoadMoreTweetsUseCase
import com.thoughtworks.moments.ui.screen.MainScreenUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: MomentRepository,
    private val getInitialTweetsUseCase: GetInitialTweetsUseCase,
    private val loadMoreTweetsUseCase: LoadMoreTweetsUseCase
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    private val _tweets = MutableStateFlow<MutableList<Tweet>>(mutableListOf())
    val tweets: StateFlow<List<Tweet>> = _tweets.asStateFlow()

    private val _uiState = MutableStateFlow(MainScreenUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadUser()
        loadTweets()
    }

    private fun loadUser() {
        _uiState.update { it.copy(isLoadingUser = true) }
        viewModelScope.launch {
            repository.fetchUser().onSuccess { userData ->
                _user.value = userData
                _uiState.update { it.copy(isLoadingUser = false) }
            }
        }
    }

    private fun loadTweets() {
        _uiState.update { it.copy(isLoadingTweets = true) }
        viewModelScope.launch {
            getInitialTweetsUseCase.invoke().onSuccess { list ->
                _tweets.value = _tweets.value.toMutableList().apply { addAll(list) }
                _uiState.update { it.copy(isLoadingTweets = false) }
            }
        }
    }

    fun loadMoreTweets() {
        if (_uiState.value.isLoadingMore) return
        _uiState.update { it.copy(isLoadingMore = true) }
        viewModelScope.launch {
            loadMoreTweetsUseCase.invoke(_tweets.value.size).onSuccess {
                _tweets.value = _tweets.value.toMutableList().apply { addAll(it) }
            }
            _uiState.update { it.copy(isLoadingMore = false) }
        }
    }

    fun refreshTweets() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            _tweets.value.clear()
//            delay(100) // Simulate network delay
            loadTweets()
            _uiState.update { it.copy(isRefreshing = false) }
        }
    }
}