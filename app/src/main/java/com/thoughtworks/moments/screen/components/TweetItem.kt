package com.thoughtworks.moments.screen.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.thoughtworks.moments.api.entry.Tweet

@Composable
fun TweetItem(
  tweet: Tweet
) {
  Text(tweet.content.orEmpty())
}

@Preview
@Composable
fun TweetItemPreview() {
  TweetItem(
    tweet = Tweet(content = "Some content")
  )
}