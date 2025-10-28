package com.thoughtworks.moments.data.remote.service

import com.thoughtworks.moments.data.dto.Tweet
import com.thoughtworks.moments.data.dto.User
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