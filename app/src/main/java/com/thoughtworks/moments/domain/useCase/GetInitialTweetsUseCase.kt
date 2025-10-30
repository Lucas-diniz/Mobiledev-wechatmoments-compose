package com.thoughtworks.moments.domain.useCase

import com.thoughtworks.moments.domain.entity.Tweet
import com.thoughtworks.moments.domain.repository.MomentRepository

class GetInitialTweetsUseCase(private val repository: MomentRepository) {
    suspend operator fun invoke(): Result<List<Tweet>> {
        return repository.fetchTweets().map { tweets ->
            tweets.take(PAGE_TWEET_COUNT)
        }
    }
    companion object {
        private const val PAGE_TWEET_COUNT = 5
    }
}