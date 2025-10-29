package com.thoughtworks.moments.domain.entity

data class Tweet(
    val name: String,
    val profilePicture: String,
    val content: String,
    val tweetImage: String?,
    val time: String
)