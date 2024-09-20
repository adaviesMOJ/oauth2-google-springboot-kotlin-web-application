package com.learning.oauth.repository

import com.learning.oauth.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByOauth2Identifier(oauth2Identifier: String): Optional<UserEntity>
}