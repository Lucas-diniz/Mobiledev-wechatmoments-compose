package com.thoughtworks.moments.api

import com.thoughtworks.moments.api.entry.Tweet
import com.thoughtworks.moments.api.entry.User
import retrofit2.http.GET
import retrofit2.http.Path

interface MomentService {

  /**
   * https://thoughtworks-ios.herokuapp.com/user/jsmith
   */
  @GET("user/{name}")
  suspend fun user(@Path("name") user: String): User

  /**
   * https://thoughtworks-ios.herokuapp.com/user/jsmith/tweets
   */
  @GET("user/{name}/tweets")
  suspend fun tweets(@Path("name") user: String): List<Tweet>

}