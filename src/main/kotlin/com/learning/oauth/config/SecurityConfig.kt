package com.learning.oauth.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain


@Configuration
class SecurityConfig {

    @Bean
    fun appSecurity(http: HttpSecurity, entryPoint: ProblemDetailsAuthenticationEntryPoint): SecurityFilterChain {
        http
            .authorizeHttpRequests { request ->
                request.anyRequest().authenticated()
            }
            .oauth2ResourceServer { oauth2 ->
                oauth2.authenticationEntryPoint(entryPoint)
                oauth2.jwt(Customizer.withDefaults())
            }
        return http.build()
    }
}
