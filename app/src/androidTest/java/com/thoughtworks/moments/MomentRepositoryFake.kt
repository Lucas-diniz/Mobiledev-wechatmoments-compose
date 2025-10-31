package com.thoughtworks.moments

import com.thoughtworks.moments.domain.entity.Tweet
import com.thoughtworks.moments.domain.entity.User
import com.thoughtworks.moments.domain.repository.MomentRepository

class MomentRepositoryFake : MomentRepository {
    override suspend fun fetchUser(): Result<User> {
        return Result.success(
            User(
                profilePicture = "https://images.squarespace-cdn.com/content/5c3e25923e2d0977a884f82c/1547654600524-XC7FKGE6RQ8VMTMUEJ70/IMG_1322.jpg?content-type=image%2Fjpeg",
                username = "username",
                userBackGround = "https://i.pinimg.com/736x/9d/53/95/9d5395b5e59b68752f869488518ae802.jpg"
            )
        )
    }

    override suspend fun fetchTweets(): Result<List<Tweet>> {
        val list: MutableList<Tweet> = mutableListOf()
        for (i in 1..22) {
            list.add(
                Tweet(
                    name = "Anonymous $i",
                    profilePicture = "https://media.sproutsocial.com/uploads/2022/06/profile-picture.jpeg",
                    content = "This is tweet number $i",
                    tweetImage =
                        "https://livdecora.com.br/blog/wp-content/webp-express/webp-images/doc-root/blog/wp-content/uploads/2017/07/decoracao-de-escritorio-porque-ter-um-escritorio-bem-decorado.jpeg.webp",
                    time = "5 mins ago"
                )
            )
        }
        return Result.success(list)
    }
}