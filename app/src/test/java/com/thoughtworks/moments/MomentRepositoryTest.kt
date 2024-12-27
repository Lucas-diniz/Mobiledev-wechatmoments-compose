package com.thoughtworks.moments

import com.thoughtworks.moments.api.MomentRepository
import com.thoughtworks.moments.api.MomentService
import com.thoughtworks.moments.api.entry.Tweet
import kotlinx.coroutines.test.runTest
import mockwebserver3.MockResponse
import mockwebserver3.junit4.MockWebServerRule
import okhttp3.ExperimentalOkHttpApi
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@OptIn(ExperimentalOkHttpApi::class)
class MomentRepositoryTest {


  @get:Rule
  val serverRule = MockWebServerRule()

  private val retrofit by lazy {
    Retrofit.Builder()
      .baseUrl(serverRule.server.url("/"))
      .addConverterFactory(MoshiConverterFactory.create())
      .build()
  }

  //TODO: Pass this fake instance to moment repository
  private val momentService by lazy {
    retrofit.create(MomentService::class.java)
  }
  private val momentRepository: MomentRepository by lazy {
    MomentRepository()
  }

  @Test
  fun fetchTweetsCallCorrectApi() = runTest {
    val server = serverRule.server

    server.enqueue(MockResponse(200, body = "[]"))

    val momentRepository = MomentRepository()
    momentRepository.fetchTweets()

    val request = server.takeRequest()

    Assert.assertEquals("/user/jsmith/tweets", request.path)
  }

  @Test
  fun parseResponseFromFetchTweets() = runTest {
    val server = serverRule.server

    val responseBody = """
      [
        {
          "sender": {
             "nick": "test-nick",
             "username": "test-username",
             "avatar": "test-avatar"
          },
          "content": "test-tweet",
          "images": [
             {
                "url": "test-image-url-one"
             }
          ],
          "comments": [
             {
                "content": "test-comment",
                "sender": {
                   "nick": "test-comment-nick",
                   "username": "test-comment-username",
                   "avatar": "test-comment-avatar"
                }
             }
          ]
         }
      ]
    """.trimIndent()

    server.enqueue(MockResponse(200, body = responseBody))

    //TODO: Test data returned from fetchTweets()
    momentRepository.fetchTweets()
  }

  // TODO: Make this test green!
  @Test
  fun skipContentWithMalformedJson() = runTest {
    val server = serverRule.server

    val responseBody = """
      [
        {
          "content": "test-tweet"
        },
        {
          "error": "some-error"
        }
      ]
    """.trimIndent()

    server.enqueue(MockResponse(200, body = responseBody))

    val momentRepository = MomentRepository()

    val actual = momentRepository.fetchTweets()

    val expected = Tweet(content = "test-tweet")

    Assert.assertEquals(expected, actual)
  }
}