package com.thoughtworks.moments.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thoughtworks.moments.ui.screen.components.TweetItem
import com.thoughtworks.moments.ui.screen.components.UserHeader
import com.thoughtworks.moments.ui.viewmodels.MainViewModel
import org.koin.androidx.compose.koinViewModel

// TODO: Write a preview for MainScreen with two sample tweets (done)
@Composable
fun MainScreen(
    mainViewModel: MainViewModel = koinViewModel()
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

    Box {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(tweets.size + 1) { index ->
                Column(modifier = Modifier.fillMaxSize()) {
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
}