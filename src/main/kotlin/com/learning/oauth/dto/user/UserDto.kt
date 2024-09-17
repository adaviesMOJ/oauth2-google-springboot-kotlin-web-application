package com.learning.oauth.dto.user

import com.learning.oauth.entity.User

data class UserDto(
    val name: String,
    val email: String,
) {
    constructor(userEntity: User): this(
        name = userEntity.name,
        email = userEntity.email,
    )
}