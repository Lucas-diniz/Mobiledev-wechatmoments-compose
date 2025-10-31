package com.thoughtworks.moments

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToIndex
import com.thoughtworks.moments.domain.repository.MomentRepository
import com.thoughtworks.moments.domain.useCase.GetInitialTweetsUseCase
import com.thoughtworks.moments.domain.useCase.LoadMoreTweetsUseCase
import com.thoughtworks.moments.ui.screen.MainScreen
import com.thoughtworks.moments.ui.viewmodels.MainViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainScreenUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    val repository: MomentRepository = MomentRepositoryFake()

    private val mainViewModel = MainViewModel(
        repository = repository,
        getInitialTweetsUseCase = GetInitialTweetsUseCase(repository),
        loadMoreTweetsUseCase = LoadMoreTweetsUseCase(repository)
    )

    @Before
    fun setup() {
        composeTestRule.setContent {
            MainScreen(mainViewModel = mainViewModel)
        }
        composeTestRule.waitForIdle()
    }

    @Test
    fun displayFirstTweet() {
        composeTestRule.onNodeWithText("This is tweet number 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Anonymous 1").assertIsDisplayed()

        val allTweetNodes = composeTestRule
            .onAllNodesWithTag("tweet_item")
            .fetchSemanticsNodes()
            .size

        composeTestRule
            .onAllNodesWithTag("tweet_item_img")
            .assertCountEquals(allTweetNodes)

        // TODO: could we test correct image is displayed? (done)
        // if it is the same as in the fake repository, is unit test responsibility
    }

    @Test
    fun displayUserHeader() {
        // TODO: Complete this test (done)
        composeTestRule.onNodeWithText("USERNAME").assertIsDisplayed()
        composeTestRule.onNodeWithTag("user_img_profile").assertIsDisplayed()
        composeTestRule.onNodeWithTag("user_img_background").assertIsDisplayed()
    }

    @Test
    fun loadMoreTweetsWhenScrollingDOwn() {
        // TODO: Complete this test (done)

        val initialTweetCount = composeTestRule
            .onAllNodesWithTag("tweet_item")
            .fetchSemanticsNodes()
            .size

        composeTestRule
            .onNodeWithTag("tweets_list")
            .performScrollToIndex(initialTweetCount - 1)

        composeTestRule.waitForIdle()

        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule
                .onAllNodesWithTag("loading_indicator")
                .fetchSemanticsNodes()
                .isEmpty()
        }

        val afterLoadCount = composeTestRule
            .onAllNodesWithTag("tweet_item")
            .fetchSemanticsNodes()
            .size

        assert(afterLoadCount > initialTweetCount) {
            "Expected more tweets after scrolling. Initial: $initialTweetCount, After: $afterLoadCount"
        }

        composeTestRule.onNodeWithText("This is tweet number 5").assertExists()
    }
}