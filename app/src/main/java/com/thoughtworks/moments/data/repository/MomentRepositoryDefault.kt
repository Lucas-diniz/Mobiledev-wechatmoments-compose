package com.thoughtworks.moments.data.repository

import com.thoughtworks.moments.data.remote.service.MomentService
import com.thoughtworks.moments.data.toDomain
import com.thoughtworks.moments.domain.entity.Tweet
import com.thoughtworks.moments.domain.entity.User
import com.thoughtworks.moments.domain.repository.MomentRepository

class MomentRepositoryDefault(
    val momentService: MomentService
) : MomentRepository {

    private var user: User? = null
    private var tweet: List<Tweet>? = null

    override suspend fun fetchUser(): Result<User> {
        user?.let { return Result.success(it) }
        return runCatching {
            momentService.user(USER).toDomain().also {
                user = it
            }
        }
    }

    override suspend fun fetchTweets(): Result<List<Tweet>> {
        tweet?.let { return Result.success(it) }
        return runCatching {
            momentService.tweets(USER).map { it.toDomain() }.also {
                tweet = it
            }
        }
    }

    companion object {
        const val USER = "jsmith"
    }
}