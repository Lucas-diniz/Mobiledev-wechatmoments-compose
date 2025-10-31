package com.thoughtworks.moments.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thoughtworks.moments.ui.screen.components.TweetItem
import com.thoughtworks.moments.ui.screen.components.UserHeader
import com.thoughtworks.moments.ui.viewmodels.MainViewModel
import org.koin.androidx.compose.koinViewModel

// TODO: Write a preview for MainScreen with two sample tweets (done)
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainScreen(mainViewModel: MainViewModel = koinViewModel()) {

    val user by mainViewModel.user.collectAsStateWithLifecycle()
    val tweets by mainViewModel.tweets.collectAsStateWithLifecycle()
    val uiState by mainViewModel.uiState.collectAsStateWithLifecycle()

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

    Box {
        PullToRefreshBox(
            isRefreshing = uiState.isRefreshing,
            onRefresh = { mainViewModel.refreshTweets() }
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("tweets_list")
            ) {
                item {
                    user?.let { UserHeader(it) }
                }
                items(
                    items = tweets,
                ) { tweet ->
                    TweetItem(tweet)
                }

                item {
                    if (uiState.isLoadingUser || uiState.isLoadingMore || uiState.isLoadingTweets || uiState.isRefreshing) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("loading_indicator"),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}