package com.thoughtworks.moments.domain.entity

import java.util.UUID

data class Tweet(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val profilePicture: String,
    val content: String,
    val tweetImage: String?,
    val time: String
)