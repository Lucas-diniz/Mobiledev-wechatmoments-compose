package com.thoughtworks.moments.data.remote.repository

import com.thoughtworks.moments.data.remote.service.MomentService
import com.thoughtworks.moments.data.remote.service.RetrofitClient
import com.thoughtworks.moments.data.toDomain
import com.thoughtworks.moments.domain.entity.Tweet
import com.thoughtworks.moments.domain.entity.User
import com.thoughtworks.moments.domain.repository.MomentRepository

class MomentRepositoryDefault(
    val momentService: MomentService = RetrofitClient.instance.create(MomentService::class.java)
) : MomentRepository {

    override suspend fun fetchUser(): User {
        return momentService.user("jsmith").toDomain()
    }

    override suspend fun fetchTweets(): List<Tweet> {
        return momentService.tweets("jsmith").map { it.toDomain() }
    }
}