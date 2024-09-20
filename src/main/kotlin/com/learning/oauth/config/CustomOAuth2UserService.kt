package com.learning.oauth.config

import com.learning.oauth.entity.UserEntity
import com.learning.oauth.repository.UserRepository
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CustomOAuth2UserService(
    private val userRepository: UserRepository
) : DefaultOAuth2UserService() {

    @Transactional
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = super.loadUser(userRequest)

        // Extract the claims (attributes)
        val attributes = oAuth2User.attributes
        val oauth2Identifier = attributes["sub"] as String
        val email = attributes["email"] as String
        val name = attributes["name"] as String

        // Store or update the user in the database
        val user = userRepository.findByOauth2Identifier(oauth2Identifier)
            .orElse(UserEntity(oauth2Identifier = oauth2Identifier, email = email, name = name))

        user.email = email
        user.name = name

        userRepository.save(user)

        return oAuth2User
    }
}