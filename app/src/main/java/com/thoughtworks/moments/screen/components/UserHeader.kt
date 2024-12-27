package com.thoughtworks.moments.screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.thoughtworks.moments.api.entry.User

@Composable
fun UserHeader(user: User) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
  ) {

    // TODO: Load user image here
    AsyncImage(
      model = "",
      contentDescription = null,
      modifier = Modifier.size(128.dp)
    )
  }
}

@Preview
@Composable
fun UserHeaderPreview() {
  // TODO: Can we have a preview for a sample image?
  UserHeader(user = User(username = "John Doe"))
}