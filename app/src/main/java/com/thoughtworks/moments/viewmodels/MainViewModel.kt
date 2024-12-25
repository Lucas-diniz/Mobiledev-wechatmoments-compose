package com.thoughtworks.moments.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thoughtworks.moments.api.MomentRepository
import com.thoughtworks.moments.api.entry.Tweet
import com.thoughtworks.moments.api.entry.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
  private val repository: MomentRepository
) : ViewModel() {

  companion object {
    private const val PAGE_TWEET_COUNT = 5
  }

  init {
    loadUser()
    loadTweets()
  }

  val user: MutableStateFlow<User?> = MutableStateFlow(null)

  private fun loadUser() {
    viewModelScope.launch {
      try {
        user.emit(repository.fetchUser())
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }

  val tweets: MutableStateFlow<List<Tweet>> = MutableStateFlow<List<Tweet>>(emptyList())

  private val _tweetsList = mutableListOf<Tweet>()
  private var allTweets: List<Tweet> = emptyList()

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