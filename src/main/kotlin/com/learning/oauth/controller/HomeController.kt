package com.learning.oauth.controller

import com.learning.oauth.annotations.CurrentUser
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

// TODO: More pages for each resource (Home, User, Cashcards, Error handling, and style with CSS).

@Controller
@RequestMapping("/", name = "Home Controller", produces = [MediaType.APPLICATION_JSON_VALUE])
class HomeController {

    @GetMapping
    fun getHome(model: Model, @CurrentUser oauth2Identifier: String): String {
        model.addAttribute("oauth2Identifier", oauth2Identifier)
        return "index"
    }

    @GetMapping("login")
    fun login(): String {
        return "redirect:/oauth2/authorization/google" // Redirect to OAuth2 authorization
    }

    @GetMapping("/logout-all")
    fun customLogout(request: HttpServletRequest): String {
        request.logout()
        return "redirect:/"
    }
}