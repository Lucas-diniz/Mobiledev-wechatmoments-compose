package com.thoughtworks.moments.domain.useCase

import com.thoughtworks.moments.domain.entity.Tweet
import com.thoughtworks.moments.domain.repository.MomentRepository

class LoadMoreTweetsUseCase(private val repository: MomentRepository) {
    suspend operator fun invoke(current: Int): Result<List<Tweet>> {
        return repository.fetchTweets().map { tweets ->
            tweets
                .drop(current)
                .take(5)
        }
    }
}