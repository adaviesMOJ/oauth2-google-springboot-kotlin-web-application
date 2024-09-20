package com.learning.oauth.dto.user

import com.learning.oauth.entity.UserEntity

data class UserDto(
    val id: Int,
    val name: String,
    val email: String,
    val oauth2Identifier: String,
) {
    constructor(userEntity: UserEntity): this(
        id = userEntity.id,
        name = userEntity.name,
        email = userEntity.email,
        oauth2Identifier = userEntity.oauth2Identifier,
    )
}