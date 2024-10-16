package com.learning.oauth.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler


@EnableMethodSecurity
@Configuration
class SecurityConfig {
    @Autowired
    private lateinit var customOAuth2UserService: CustomOAuth2UserService

    @Bean
    fun appSecurity(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { request ->
                request.requestMatchers("/", "/login", "/error").permitAll()
                .anyRequest().authenticated()
            }
            .oauth2Login { oauth2 ->
                oauth2.userInfoEndpoint { userInfo ->
                    userInfo.userService(customOAuth2UserService)
                }
                .loginPage("/login")
                .defaultSuccessUrl("/")
            }
            .logout { logout ->
                logout
                    .logoutSuccessUrl("/")
                    .permitAll()
            }

        return http.build()
    }

    @Bean
    fun logoutSuccessHandler(): LogoutSuccessHandler {
        return SimpleUrlLogoutSuccessHandler().apply {
            setDefaultTargetUrl("/") // Redirect to home after logout
            setAlwaysUseDefaultTargetUrl(true)
        }
    }
}
