package com.thoughtworks.moments.domain.entity

data class User(
    val profilePicture: String,
    val username: String,
    val userBackGround: String? = null
)