package com.thoughtworks.moments

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToIndex
import com.thoughtworks.moments.api.MomentRepository
import com.thoughtworks.moments.screen.MainScreen
import com.thoughtworks.moments.viewmodels.MainViewModel
import org.junit.Rule
import org.junit.Test

class MainScreenUiTest {

  @get:Rule
  val composeTestRule = createComposeRule()

  private val mainViewModel = MainViewModel(MomentRepository())

  @Test
  fun displayFirstFiveTweets() {
    composeTestRule.setContent {
      MainScreen(mainViewModel = mainViewModel)
    }

    composeTestRule.onNodeWithText("Cheng Yao").assertIsDisplayed()
    composeTestRule.onNodeWithText("First post!").assertIsDisplayed()

    composeTestRule.onNodeWithText("Xin Ge").assertIsDisplayed()

    composeTestRule.onNodeWithText("Yang Luo").assertIsDisplayed()

    composeTestRule.onNodeWithText("Jianing Zheng").assertIsDisplayed()

    composeTestRule.onNodeWithText("Wei Fan").assertIsDisplayed()

    // TODO: could we test correct image is displayed?
  }

  @Test
  fun displayUserHeader() {
    // TODO: Complete this test
  }

  @Test
  fun loadMoreTweetsWhenScrollingDOwn() {
    // TODO: Complete this test
    composeTestRule.onNodeWithTag("").performScrollToIndex(4)
  }
}