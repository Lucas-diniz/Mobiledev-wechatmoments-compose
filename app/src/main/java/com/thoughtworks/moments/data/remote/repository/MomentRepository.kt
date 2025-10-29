package com.thoughtworks.moments.data.remote.repository

import com.thoughtworks.moments.data.dto.Tweet
import com.thoughtworks.moments.data.dto.User
import com.thoughtworks.moments.data.remote.service.MomentService
import com.thoughtworks.moments.data.remote.service.RetrofitClient

class MomentRepository {

    private val momentService = RetrofitClient.instance.create(MomentService::class.java)

    suspend fun fetchUser(): User {
        return momentService.user("jsmith")
    }

    suspend fun fetchTweets(): List<Tweet> {
        return momentService.tweets("jsmith")
    }

}