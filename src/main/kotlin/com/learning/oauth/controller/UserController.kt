package com.learning.oauth.controller

import com.learning.oauth.entity.User
import com.learning.oauth.service.UserService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

const val POST_USER = "/users"
const val GET_USER = "/users/{id}"


@RestController
@RequestMapping(name = "User Resource", produces = [MediaType.APPLICATION_JSON_VALUE])
class UserController(
    private val userService: UserService,
) {
    @GetMapping(GET_USER)
    fun getUser(@PathVariable id: Long): User? {
        return userService.getUser(id)
    }

    @PostMapping(POST_USER)
    fun saveUser(@RequestBody user: User): User {
        return userService.saveUser(user)
    }
}