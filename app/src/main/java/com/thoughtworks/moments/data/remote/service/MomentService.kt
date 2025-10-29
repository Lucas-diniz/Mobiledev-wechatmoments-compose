package com.thoughtworks.moments.data.remote.service

import com.thoughtworks.moments.data.dto.TweetDto
import com.thoughtworks.moments.data.dto.UserDto
import retrofit2.http.GET
import retrofit2.http.Path

interface MomentService {

    /**
     * https://thoughtworks-ios.herokuapp.com/user/jsmith
     */
    @GET("user/{name}")
    suspend fun user(@Path("name") user: String): UserDto

    /**
     * https://thoughtworks-ios.herokuapp.com/user/jsmith/tweets
     */
    @GET("user/{name}/tweets")
    suspend fun tweets(@Path("name") user: String): List<TweetDto>

}