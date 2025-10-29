package com.thoughtworks.moments.data

import com.thoughtworks.moments.data.dto.TweetDto
import com.thoughtworks.moments.data.dto.UserDto
import com.thoughtworks.moments.domain.entity.Tweet
import com.thoughtworks.moments.domain.entity.User

fun TweetDto.toDomain(): Tweet {
    return Tweet(
        name = "Anonymous",
        profilePicture = "https://media.sproutsocial.com/uploads/2022/06/profile-picture.jpeg",
        content = this.content ?: "Empty content",
        tweetImage = "https://livdecora.com.br/blog/wp-content/webp-express/webp-images/doc-root/blog/wp-content/uploads/2017/07/decoracao-de-escritorio-porque-ter-um-escritorio-bem-decorado.jpeg.webp",
        time = "5 mins ago"
    )
}

fun UserDto.toDomain(): User {
    return User(
        profilePicture = "https://images.squarespace-cdn.com/content/5c3e25923e2d0977a884f82c/1547654600524-XC7FKGE6RQ8VMTMUEJ70/IMG_1322.jpg?content-type=image%2Fjpeg",
        username = this.username ?: "Anonymous",
        userBackGround = "https://i.pinimg.com/736x/9d/53/95/9d5395b5e59b68752f869488518ae802.jpg"
    )
}