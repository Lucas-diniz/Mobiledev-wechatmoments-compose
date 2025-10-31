package com.thoughtworks.moments.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thoughtworks.moments.domain.entity.Tweet
import com.thoughtworks.moments.domain.entity.User
import com.thoughtworks.moments.domain.repository.MomentRepository
import com.thoughtworks.moments.domain.useCase.GetInitialTweetsUseCase
import com.thoughtworks.moments.domain.useCase.LoadMoreTweetsUseCase
import com.thoughtworks.moments.ui.screen.MainScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: MomentRepository,
    private val getInitialTweetsUseCase: GetInitialTweetsUseCase,
    private val loadMoreTweetsUseCase: LoadMoreTweetsUseCase
) : ViewModel() {

    val user: MutableStateFlow<User?> = MutableStateFlow(null)
    val tweets: MutableStateFlow<List<Tweet>> = MutableStateFlow(emptyList())
    private val _tweetsList = mutableListOf<Tweet>()

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
                user.emit(userData)
                _uiState.update { it.copy(isLoadingUser = false) }
            }
        }
    }

    private fun loadTweets() {
        _uiState.update { it.copy(isLoadingTweets = true) }
        viewModelScope.launch {
            getInitialTweetsUseCase.invoke().onSuccess {
                _uiState.update { it.copy(isLoadingTweets = false) }
                tweets.emit(_tweetsList.toList())
            }
        }
    }

    fun loadMoreTweets() {
        if (_uiState.value.isLoadingMore) return
        _uiState.update { it.copy(isLoadingMore = true) }
        viewModelScope.launch {
            loadMoreTweetsUseCase.invoke(_tweetsList.size).onSuccess {
                _tweetsList.addAll(it)
                tweets.emit(_tweetsList.toList())
            }
            _uiState.update { it.copy(isLoadingMore = false) }
        }
    }

    fun refreshTweets() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            loadTweets()
            _uiState.update { it.copy(isRefreshing = false) }
        }
    }
}