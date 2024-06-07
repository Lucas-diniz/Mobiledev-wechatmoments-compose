package com.thoughtworks.moments.api.entry

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Tweet(
  val content: String?
)