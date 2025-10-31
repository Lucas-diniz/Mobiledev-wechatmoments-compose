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

class GetInitialTweetsUseCaseTest {
    private val repository: MomentRepository = mockk()
    private val getInitialTweetsUseCase = GetInitialTweetsUseCase(repository)

    @After
    fun setDown() {
        confirmVerified(repository)
        unmockkAll()
    }

    @Test
    fun `test invoke success`() = runBlocking {
        coEvery { repository.fetchTweets() } returns Result.success(tweetList)

        val result = getInitialTweetsUseCase.invoke()

        coVerify(exactly = 1) { repository.fetchTweets() }
        assertEquals(true, result.isSuccess)
        assertEquals(tweetList[0], result.getOrNull()!![0])
        assertEquals(tweetList[1], result.getOrNull()!![1])
        assertEquals(tweetList[2], result.getOrNull()!![2])
        assertEquals(tweetList[3], result.getOrNull()!![3])
        assertEquals(tweetList[4], result.getOrNull()!![4])
    }

    @Test
    fun `test invoke error`() = runBlocking {
        coEvery { repository.fetchTweets() } returns Result.failure(Exception("Network Error"))

        val result = getInitialTweetsUseCase.invoke()

        coVerify(exactly = 1) { repository.fetchTweets() }
        assertEquals(true, result.isFailure)
        assertEquals("Network Error", result.exceptionOrNull()?.message)
    }

    @Test
    fun `verify if return just first 5 items`() = runBlocking {
        coEvery { repository.fetchTweets() } returns Result.success(tweetList)

        val result = getInitialTweetsUseCase.invoke()

        coVerify(exactly = 1) { repository.fetchTweets() }
        assertEquals(5, result.getOrNull()!!.size)
    }
}