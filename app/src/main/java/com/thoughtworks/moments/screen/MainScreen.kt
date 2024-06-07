package com.thoughtworks.moments.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.thoughtworks.moments.api.entry.Tweet
import com.thoughtworks.moments.api.entry.User
import com.thoughtworks.moments.screen.components.TweetItem
import com.thoughtworks.moments.screen.components.UserHeader
import com.thoughtworks.moments.viewmodels.MainViewModel

@Composable
fun MainScreen(
  mainViewModel: MainViewModel
) {
  val user = mainViewModel.user.collectAsState(initial = null).value
  val tweets = mainViewModel.tweets.collectAsState(initial = emptyList()).value

  if (user != null) {
    MainScreenContent(user, tweets)
  }
}

@Composable
private fun MainScreenContent(
  user: User,
  tweets: List<Tweet>
) {
  Scaffold { paddingValues ->
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
      items(tweets.size + 1) { index ->
        if (index == 0) {
          UserHeader(user = user)
        } else {
          TweetItem(tweets[index - 1])
        }
      }
    }
  }
}


@Preview
@Composable
fun MainScreenContentPreview() {
  // TODO: Preview MainScreenContent with sample data
}