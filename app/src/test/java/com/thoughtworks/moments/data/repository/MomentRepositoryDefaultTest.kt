package com.thoughtworks.moments.data.repository

import com.thoughtworks.moments.data.dto.TweetDto
import com.thoughtworks.moments.data.dto.UserDto
import com.thoughtworks.moments.data.remote.service.MomentService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test


//TODO: Pass this fake instance to moment repository (done)
//TODO: Test data returned from fetchTweets() (done)
//TODO: Make this test green! (done)
class MomentRepositoryDefaultTest {
    private val momentService: MomentService = mockk()
    private val momentRepositoryDefault = MomentRepositoryDefault(momentService)

    private val user = UserDto(
        username = "username"
    )

    private val tweetDto = TweetDto(
        content = "content",
    )

    val listTweet = listOf(tweetDto, tweetDto, tweetDto)

    @After
    fun setDown() {
        confirmVerified(momentService)
        unmockkAll()
    }

    @Test
    fun `test fetchUser should return service data and success`(): Unit = runTest {
        coEvery { momentService.user(any()) } returns user

        val result = momentRepositoryDefault.fetchUser()

        coVerify(exactly = 1) { momentService.user("jsmith") }

        assertTrue(result.isSuccess)
        assertEquals("username", result.getOrNull()?.username)
    }

    @Test
    fun `test fetchUser should return cache data and success`(): Unit = runTest {
        coEvery { momentService.user(any()) } returns user

        momentRepositoryDefault.fetchUser()
        val result = momentRepositoryDefault.fetchUser()

        coVerify(exactly = 1) { momentService.user("jsmith") }

        assertTrue(result.isSuccess)
        assertEquals("username", result.getOrNull()?.username)
    }

    @Test
    fun `test fetchUser should return default data and success`(): Unit = runTest {
        coEvery { momentService.user(any()) } returns UserDto(null)

        val result = momentRepositoryDefault.fetchUser()

        coVerify(exactly = 1) { momentService.user("jsmith") }

        assertTrue(result.isSuccess)
        assertEquals("Anonymous", result.getOrNull()?.username)
        assertEquals(
            "https://images.squarespace-cdn.com/content/5c3e25923e2d0977a884f82c/1547654600524-XC7FKGE6RQ8VMTMUEJ70/IMG_1322.jpg?content-type=image%2Fjpeg",
            result.getOrNull()?.profilePicture
        )
        assertEquals(
            "https://i.pinimg.com/736x/9d/53/95/9d5395b5e59b68752f869488518ae802.jpg",
            result.getOrNull()?.userBackGround
        )
    }

    @Test
    fun `test fetchTweets should return service data and success`(): Unit = runTest {

        coEvery { momentService.tweets(any()) } returns listTweet

        val result = momentRepositoryDefault.fetchTweets()

        coVerify(exactly = 1) { momentService.tweets("jsmith") }

        assertTrue(result.isSuccess)
        assertEquals("content", result.getOrNull()!![0].content)
        assertEquals("content", result.getOrNull()!![1].content)
        assertEquals("content", result.getOrNull()!![2].content)
    }

    @Test
    fun `test fetchTweets should return cache data and success`(): Unit = runTest {
        coEvery { momentService.tweets(any()) } returns listTweet

        momentRepositoryDefault.fetchTweets()
        val result = momentRepositoryDefault.fetchTweets()

        coVerify(exactly = 1) { momentService.tweets("jsmith") }

        assertTrue(result.isSuccess)
        assertEquals("content", result.getOrNull()!![0].content)
        assertEquals("content", result.getOrNull()!![1].content)
        assertEquals("content", result.getOrNull()!![2].content)
    }

    @Test
    fun `test fetchTweets should return default data and success`(): Unit = runTest {
        coEvery { momentService.tweets(any()) } returns listOf(TweetDto(null))

        val result = momentRepositoryDefault.fetchTweets()

        coVerify(exactly = 1) { momentService.tweets("jsmith") }

        assertTrue(result.isSuccess)
        assertEquals("Anonymous", result.getOrNull()!![0].name)
        assertEquals(
            "https://media.sproutsocial.com/uploads/2022/06/profile-picture.jpeg",
            result.getOrNull()!![0].profilePicture
        )
        assertEquals("Empty content", result.getOrNull()!![0].content)
        assertEquals(
            "https://livdecora.com.br/blog/wp-content/webp-express/webp-images/doc-root/blog/wp-content/uploads/2017/07/decoracao-de-escritorio-porque-ter-um-escritorio-bem-decorado.jpeg.webp",
            result.getOrNull()!![0].tweetImage
        )
        assertEquals("5 mins ago", result.getOrNull()!![0].time)
    }
}