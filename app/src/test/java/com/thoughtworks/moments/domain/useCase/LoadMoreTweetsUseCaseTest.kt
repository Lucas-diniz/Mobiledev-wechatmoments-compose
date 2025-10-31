package com.thoughtworks.moments.domain.useCase

import com.thoughtworks.moments.domain.repository.MomentRepository
import com.thoughtworks.moments.fixture.AppFixtures.tweetList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test

class LoadMoreTweetsUseCaseTest {
    private val repository: MomentRepository = mockk()
    private val loadMoreTweetsUseCase = LoadMoreTweetsUseCase(repository)

    @After
    fun setDown() {
        confirmVerified(repository)
        unmockkAll()
    }

    @Test
    fun `test invoke success`(): Unit = runBlocking {
        coEvery { repository.fetchTweets() } returns Result.success(tweetList)

        val result = loadMoreTweetsUseCase.invoke(5)

        coVerify(exactly = 1) { repository.fetchTweets() }
        assertEquals(true, result.isSuccess)
        assertEquals(tweetList[5], result.getOrNull()!![0])
        assertEquals(tweetList[6], result.getOrNull()!![1])
        assertEquals(tweetList[7], result.getOrNull()!![2])
        assertEquals(tweetList[8], result.getOrNull()!![3])
        assertEquals(tweetList[9], result.getOrNull()!![4])

    }

    @Test
    fun `test invoke error`() = runBlocking {
        coEvery { repository.fetchTweets() } returns Result.failure(Exception("Network Error"))

        val result = loadMoreTweetsUseCase.invoke(5)

        coVerify(exactly = 1) { repository.fetchTweets() }
        assertEquals(true, result.isFailure)
        assertEquals("Network Error", result.exceptionOrNull()?.message)
    }
}