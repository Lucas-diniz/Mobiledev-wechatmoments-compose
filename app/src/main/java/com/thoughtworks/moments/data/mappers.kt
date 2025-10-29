package com.thoughtworks.moments.data

import com.thoughtworks.moments.data.dto.TweetDto
import com.thoughtworks.moments.data.dto.UserDto
import com.thoughtworks.moments.domain.entity.Tweet
import com.thoughtworks.moments.domain.entity.User


fun TweetDto.toDomain(): Tweet {
    return Tweet(
        content = this.content ?: "No Content"
    )
}

fun UserDto.toDomain(): User {
    return User(
        username = this.username ?: "Anonymous"
    )
}