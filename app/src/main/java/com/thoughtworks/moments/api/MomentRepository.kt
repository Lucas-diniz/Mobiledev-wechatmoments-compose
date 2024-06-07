package com.thoughtworks.moments.api

import com.thoughtworks.moments.api.entry.Tweet
import com.thoughtworks.moments.api.entry.User

class MomentRepository {

  private val momentService = RetrofitClient.instance.create(MomentService::class.java)

  suspend fun fetchUser(): User {
    return momentService.user("jsmith")
  }

  suspend fun fetchTweets(): List<Tweet> {
    return momentService.tweets("jsmith")
  }

}