package com.thoughtworks.moments.screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.thoughtworks.moments.api.entry.Tweet

@Composable
fun TweetItem(
  tweet: Tweet
) {
  Text(tweet.content.orEmpty())
}

// TODO: Rework this to be more user friendly for different form factors
@Composable
private fun ImageGrid(imageUrl: List<String>) {
  if (imageUrl.isEmpty()) {
    return
  } else if (imageUrl.size == 1) {
    AsyncImage(
      model = imageUrl[0],
      contentDescription = null,
      modifier = Modifier
        .width(100.dp)
        .height(150.dp),
      contentScale = ContentScale.Crop
    )
  } else {
    val columns = if (imageUrl.size == 4) {
      2
    } else {
      3
    }
    Grid(
      columns = columns,
      itemCount = imageUrl.size,
      verticalPadding = 8.dp,
      horizontalPadding = 16.dp,
      content = { index ->
        AsyncImage(
          model = imageUrl[index],
          contentDescription = null,
          modifier = Modifier
            .size(94.dp),
          contentScale = ContentScale.Crop
        )
      }
    )
  }
}


@Preview
@Composable
fun TweetItemPreview() {
  TweetItem(
    tweet = Tweet(content = "Some content")
  )
}