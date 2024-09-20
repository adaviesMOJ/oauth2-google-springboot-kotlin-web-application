package com.learning.oauth.config

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ProblemDetail
import org.springframework.security.core.AuthenticationException
import org.springframework.security.oauth2.jwt.JwtValidationException
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.net.URI


@Component
class ProblemDetailsAuthenticationEntryPoint(
    val objectMapper: ObjectMapper
) : AuthenticationEntryPoint {

    private val delegate: AuthenticationEntryPoint = BearerTokenAuthenticationEntryPoint()

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException,
    ) {
        delegate.commence(request, response, authException)

        val cause = authException.cause
        if (cause is JwtValidationException) {
            val detail = ProblemDetail.forStatus(401).apply {
                type = URI("https://tools.ietf.org/html/rfc6750#section-3.1")
                title = "Invalid Token"
                setProperty("errors", cause.errors)
            }
            objectMapper.writeValue(response.writer, detail)
        }
    }
}