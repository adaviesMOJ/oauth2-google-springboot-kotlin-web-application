package com.learning.oauth.dto.user

import com.learning.oauth.entity.UserEntity

data class UserDto(
    val id: Long,
    val name: String,
    val email: String,
) {
    constructor(userEntity: UserEntity): this(
        id = userEntity.id,
        name = userEntity.name,
        email = userEntity.email,
    )
}