package com.thoughtworks.moments.ui

import com.thoughtworks.moments.CoroutineTestRule
import com.thoughtworks.moments.domain.entity.Tweet
import com.thoughtworks.moments.domain.entity.User
import com.thoughtworks.moments.domain.repository.MomentRepository
import com.thoughtworks.moments.domain.useCase.GetInitialTweetsUseCase
import com.thoughtworks.moments.domain.useCase.LoadMoreTweetsUseCase
import com.thoughtworks.moments.fixture.AppFixtures.tweetList
import com.thoughtworks.moments.ui.viewmodels.MainViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test

// TODO : Fix this test
// TODO : Complete this test before coding for pagination
@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    val repository: MomentRepository = mockk()
    val getInitialTweetsUseCase: GetInitialTweetsUseCase = mockk()
    val loadMoreTweetsUseCase: LoadMoreTweetsUseCase = mockk()
    private lateinit var mainViewModel: MainViewModel

    val user = User(
        username = "username",
        profilePicture = "profilePicture",
        userBackGround = "userBackGround",
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        coEvery { repository.fetchUser() } returns Result.success(user)
        coEvery { getInitialTweetsUseCase.invoke() } returns Result.success(tweetList.take(5))

        mainViewModel = MainViewModel(
            repository = repository,
            getInitialTweetsUseCase = getInitialTweetsUseCase,
            loadMoreTweetsUseCase = loadMoreTweetsUseCase
        )
    }

    @After
    fun setDown() {
        Dispatchers.resetMain()
        confirmVerified(
            repository,
            getInitialTweetsUseCase,
            loadMoreTweetsUseCase
        )
        unmockkAll()
    }

    @Test
    fun loadMoreTweets(): Unit = runBlocking {
        coEvery { loadMoreTweetsUseCase.invoke(any()) } returns Result.success(tweetList.take(5))

        mainViewModel.loadMoreTweets()

        coVerify(exactly = 1) { repository.fetchUser() }
        coVerify(exactly = 1) { getInitialTweetsUseCase.invoke() }
        coVerify(exactly = 1) { loadMoreTweetsUseCase.invoke(5) }

        assertEquals(10, mainViewModel.tweets.value.size)
        assertFalse(mainViewModel.uiState.value.isLoadingMore)
    }

    @Test
    fun refreshTweets(): Unit = runBlocking {
        coEvery { loadMoreTweetsUseCase.invoke(any()) } returns Result.success(tweetList.take(5))

        mainViewModel.refreshTweets()

        coVerify(exactly = 1) { repository.fetchUser() }
        coVerify(exactly = 2) { getInitialTweetsUseCase.invoke() }
        coVerify(exactly = 0) { loadMoreTweetsUseCase.invoke(5) }

        assertEquals(5, mainViewModel.tweets.value.size)
        assertFalse(mainViewModel.uiState.value.isLoadingMore)
    }
}