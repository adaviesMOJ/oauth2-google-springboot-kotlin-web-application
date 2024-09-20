package com.learning.oauth.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain


@EnableMethodSecurity
@Configuration
class SecurityConfig {

    @Bean
    fun appSecurity(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { request ->
                request.anyRequest().authenticated()
            }
            .oauth2Login(Customizer.withDefaults())

        return http.build()
    }
}
