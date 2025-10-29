package com.thoughtworks.moments.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thoughtworks.moments.data.remote.repository.MomentRepository
import com.thoughtworks.moments.data.dto.Tweet
import com.thoughtworks.moments.data.dto.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: MomentRepository
) : ViewModel() {

    val user: MutableStateFlow<User?> = MutableStateFlow(null)

    val tweets: MutableStateFlow<List<Tweet>> = MutableStateFlow<List<Tweet>>(emptyList())
    private val _tweetsList = mutableListOf<Tweet>()

    private var allTweets: List<Tweet> = emptyList()

    companion object {
        private const val PAGE_TWEET_COUNT = 5
    }

    init {
        loadUser()
        loadTweets()
    }

    private fun loadUser() {
        viewModelScope.launch {
            try {
                user.emit(repository.fetchUser())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun loadTweets() {
        viewModelScope.launch {
            allTweets = try {
                repository.fetchTweets()
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }

            _tweetsList.addAll(allTweets.subList(0, PAGE_TWEET_COUNT.coerceAtMost(allTweets.size)))
            tweets.emit(_tweetsList)
        }
    }

    fun loadMoreTweets() {
        viewModelScope.launch {
            // TODO: Implement Pagination
            tweets.emit(_tweetsList)
        }
    }
}