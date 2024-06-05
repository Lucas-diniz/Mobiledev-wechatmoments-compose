package com.thoughtworks.moments.api.entry

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
  val username: String?
)