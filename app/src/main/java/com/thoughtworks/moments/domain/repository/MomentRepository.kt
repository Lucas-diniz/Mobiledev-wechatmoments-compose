package com.thoughtworks.moments.domain.repository

import com.thoughtworks.moments.domain.entity.Tweet
import com.thoughtworks.moments.domain.entity.User

interface MomentRepository {
    suspend fun fetchUser(): Result<User>
    suspend fun fetchTweets(): Result<List<Tweet>>
}