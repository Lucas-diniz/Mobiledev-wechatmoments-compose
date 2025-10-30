package com.thoughtworks.moments.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thoughtworks.moments.domain.entity.Tweet
import com.thoughtworks.moments.domain.entity.User
import com.thoughtworks.moments.domain.repository.MomentRepository
import com.thoughtworks.moments.domain.useCase.GetInitialTweetsUseCase
import com.thoughtworks.moments.domain.useCase.LoadMoreTweetsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: MomentRepository,
    private val getInitialTweetsUseCase: GetInitialTweetsUseCase,
    private val loadMoreTweetsUseCase: LoadMoreTweetsUseCase
) : ViewModel() {

    val user: MutableStateFlow<User?> = MutableStateFlow(null)
    val tweets: MutableStateFlow<List<Tweet>> = MutableStateFlow(emptyList())
    private val _tweetsList = mutableListOf<Tweet>()

    private var isLoadingMore = false

    init {
        loadUser()
        loadTweets()
    }

    private fun loadUser() {
        viewModelScope.launch {
            repository.fetchUser().onSuccess {
                user.emit(it)
            }
        }
    }

    private fun loadTweets() {
        viewModelScope.launch {
            getInitialTweetsUseCase.invoke().onSuccess {
                tweets.emit(_tweetsList.toList())
            }
        }
    }

    fun loadMoreTweets() {
        if (isLoadingMore) return

        isLoadingMore = true
        viewModelScope.launch {
            loadMoreTweetsUseCase.invoke(_tweetsList.size).onSuccess {
                _tweetsList.addAll(it)
                tweets.emit(_tweetsList.toList())
            }
            isLoadingMore = false
        }
    }
}