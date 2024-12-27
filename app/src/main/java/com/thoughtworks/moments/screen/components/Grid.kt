package com.thoughtworks.moments.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Grid(
  columns: Int,
  itemCount: Int,
  content: @Composable() RowScope.(index: Int) -> Unit,
  verticalPadding: Dp = 0.dp,
  horizontalPadding: Dp = 0.dp,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(verticalPadding)
  ) {
    var rows = (itemCount / columns)
    if (itemCount.mod(columns) > 0) {
      rows += 1
    }

    for (rowId in 0 until rows) {
      val firstIndex = rowId * columns

      Row(
        horizontalArrangement = Arrangement.spacedBy(horizontalPadding)
      ) {
        for (columnId in 0 until columns) {
          val index = firstIndex + columnId
          if (index < itemCount) {
            content(index)
          }
        }
      }
    }
  }
}

@Preview
@Composable
fun GridPreview() {
  Grid(
    columns = 3,
    itemCount = 5,
    verticalPadding = 8.dp,
    horizontalPadding = 8.dp,
    content = { index ->
      Box(
        modifier = Modifier
          .background(Color.Blue)
          .size(48.dp)
      ) {
        Text("Item: $index", modifier = Modifier.align(Alignment.Center))
      }
    }
  )
}
