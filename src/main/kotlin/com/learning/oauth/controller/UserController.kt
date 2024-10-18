package com.learning.oauth.controller

import com.learning.oauth.annotations.CurrentUser
import com.learning.oauth.dto.user.UserDto
import com.learning.oauth.service.UserService
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PostAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

const val BASE_USERS: String = "/users"
const val GET_USER_ME: String = "/me"

@RestController
@RequestMapping(BASE_USERS, name = "User Controller", produces = [MediaType.APPLICATION_JSON_VALUE])
class UserController(
    private val userService: UserService,
) {
    @PostAuthorize("returnObject.oauth2Identifier == authentication.name")
    @GetMapping(GET_USER_ME)
    @ResponseBody
    fun getUser(@CurrentUser oauth2Identifier: String): UserDto {
        return UserDto(userService.getUserByOauth2Identifier(oauth2Identifier))
    }
}