package com.thoughtworks.moments

import app.cash.turbine.test
import com.thoughtworks.moments.api.MomentRepository
import com.thoughtworks.moments.api.entry.Tweet
import com.thoughtworks.moments.api.entry.User
import com.thoughtworks.moments.viewmodels.MainViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

  @get:Rule
  val coroutineTestRule = CoroutineTestRule()

  private val testDispatcher: TestDispatcher = StandardTestDispatcher()
  private val tweets = (1..10).map {
    Tweet(content = "Tweet no. $it")
  }
  private val user = User(username = "some user")
  private val repository: MomentRepository = mockk() {
    coEvery {
      fetchTweets()
    } returns tweets
    coEvery {
      fetchUser()
    } returns user
  }
  private val mainViewModel by lazy {
    MainViewModel(repository)
  }

  @OptIn(ExperimentalCoroutinesApi::class)
  @Before
  fun setup() {
    Dispatchers.setMain(testDispatcher)
  }

  @Test
  fun initialize_tweets_with_empty_list() = runTest {
    mainViewModel.tweets.test {
      Assert.assertEquals(emptyList<Tweet>(), awaitItem())
      cancelAndConsumeRemainingEvents()
    }
  }

  // TODO : Fix this test
  @Test
  fun fetch_tweets_on_initialization() = runTest {
    coEvery {
      repository.fetchTweets()
    } returns tweets
    coEvery {
      repository.fetchUser()
    } returns User(username = "some user")

    val expected = tweets.subList(0, 5)

    mainViewModel.tweets.test {
      Assert.assertEquals(expected, awaitItem())
    }
  }

  @Test
  fun load_more_tweets_add_5_more_items() = runTest {
    mainViewModel.tweets.test {
      // TODO : Complete this test before coding for pagination
    }
  }
}