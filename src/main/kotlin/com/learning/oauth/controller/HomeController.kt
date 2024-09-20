package com.learning.oauth.controller

import com.learning.oauth.annotations.CurrentUser
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/", name = "Home Controller", produces = [MediaType.APPLICATION_JSON_VALUE])
class HomeController {
    @GetMapping
    fun getUser(@CurrentUser oauth2Identifier: String): String {
        return "Hello $oauth2Identifier"
    }
}