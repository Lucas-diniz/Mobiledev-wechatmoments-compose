package com.thoughtworks.moments.data.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDto(
    val username: String?
)