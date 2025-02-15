package com.learning.oauth.dto.user

data class CreateUserDto(
    val name: String,
    val email: String,
    val oauth2Identifier: String,
)