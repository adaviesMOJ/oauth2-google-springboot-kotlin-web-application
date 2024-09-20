package com.learning.oauth.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain


@EnableMethodSecurity
@Configuration
class SecurityConfig {
    @Autowired
    private lateinit var customOAuth2UserService: CustomOAuth2UserService

    @Bean
    fun appSecurity(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { request ->
                request.anyRequest().authenticated()
            }
            .oauth2Login { oauth2 ->
                oauth2.userInfoEndpoint { userInfo ->
                    userInfo.userService(customOAuth2UserService)
                }
            }

        return http.build()
    }
}
