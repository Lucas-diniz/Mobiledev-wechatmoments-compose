package com.thoughtworks.moments.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thoughtworks.moments.api.MomentRepository
import com.thoughtworks.moments.api.entry.Tweet
import com.thoughtworks.moments.api.entry.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainViewModel(
  private val repository: MomentRepository
) : ViewModel() {

  companion object {
    private const val PAGE_TWEET_COUNT = 5
  }

  val user: Flow<User> by lazy {
    flow {
      viewModelScope.launch {
        try {
          this@flow.emit(repository.fetchUser())
        } catch (e: Exception) {
          e.printStackTrace()
        }
      }
    }
  }

  val _tweets: MutableStateFlow<List<Tweet>> by lazy {
    MutableStateFlow<List<Tweet>>(emptyList()).also {
      loadTweets()
    }
  }

  private val _tweetsList = mutableListOf<Tweet>()
  val tweets: Flow<List<Tweet>> get() = _tweets

  private var allTweets: List<Tweet> = emptyList()

  private fun loadTweets() {
    viewModelScope.launch {
      allTweets = try {
        repository.fetchTweets()
      } catch (e: Exception) {
        e.printStackTrace()
        emptyList()
      }

      _tweetsList.addAll(allTweets.subList(0, PAGE_TWEET_COUNT.coerceAtMost(_tweetsList.size)))
    }
  }

  // TODO: Remove if not needed
  fun refreshTweets() {
    loadTweets()
  }

  fun loadMoreTweets() {
    // TODO: Pagination
  }
}