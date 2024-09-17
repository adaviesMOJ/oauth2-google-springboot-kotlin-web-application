package com.learning.oauth.service

import com.learning.oauth.repository.UserRepository
import com.learning.oauth.entity.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
) {
    fun getUser(id: Long): User? {
        return userRepository.findById(id).orElse(null)
    }

    fun saveUser(user: User): User {
        return userRepository.save(user)
    }
}