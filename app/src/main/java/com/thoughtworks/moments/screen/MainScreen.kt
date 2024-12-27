package com.thoughtworks.moments.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thoughtworks.moments.screen.components.TweetItem
import com.thoughtworks.moments.screen.components.UserHeader
import com.thoughtworks.moments.viewmodels.MainViewModel

@Composable
fun MainScreen(
  mainViewModel: MainViewModel
) {
  val user by mainViewModel.user.collectAsStateWithLifecycle()
  val tweets by mainViewModel.tweets.collectAsStateWithLifecycle()
  val listState = rememberLazyListState()
  val endOfListReached by remember {
    derivedStateOf {
      listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == listState.layoutInfo.totalItemsCount - 1
    }
  }

  LaunchedEffect(endOfListReached) {
    if (endOfListReached) {
      mainViewModel.loadMoreTweets()
    }
  }

  Scaffold { paddingValues ->
    LazyColumn(
      state = listState,
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
      items(tweets.size + 1) { index ->
        if (index == 0) {
          user?.let {
            UserHeader(user = it)
          }
        } else {
          TweetItem(tweets[index - 1])
        }
      }
    }
  }
}


@Preview
@Composable
fun MainScreenPreview() {
  // TODO: Write a preview for MainScreen with two sample tweets
}