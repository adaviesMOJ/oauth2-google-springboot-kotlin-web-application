package com.learning.oauth.controller

import com.learning.oauth.dto.user.CreateUserDto
import com.learning.oauth.dto.user.UserDto
import com.learning.oauth.service.UserService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

const val BASE_USERS: String = "/users"
const val GET_USER_BY_ID: String = "/{id}"

@RestController
@RequestMapping(BASE_USERS, name = "User Controller", produces = [MediaType.APPLICATION_JSON_VALUE])
class UserController(
    private val userService: UserService,
) {
    @PostMapping
    fun saveUser(@RequestBody user: CreateUserDto): UserDto {
        return UserDto(userService.saveUser(user))
    }

    @GetMapping
    fun getAllUsers(): List<UserDto> {
        return userService.getAllUsers().map { UserDto(it) }
    }

    @GetMapping(GET_USER_BY_ID)
    fun getUser(@PathVariable id: Long): UserDto {
        return UserDto(userService.getUser(id))
    }

}