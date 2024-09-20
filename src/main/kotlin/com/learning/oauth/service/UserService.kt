package com.learning.oauth.service

import com.learning.oauth.dto.user.CreateUserDto
import com.learning.oauth.entity.UserEntity
import com.learning.oauth.repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
) {
    fun saveUser(user: CreateUserDto): UserEntity {
        val userEntity = UserEntity(name = user.name, email = user.email)
        return userRepository.save(userEntity)
    }

    fun getUserByUsername(username: String): UserEntity {
        val user = userRepository.findByUsername(username)
        if (!user.isPresent) {
            throw EntityNotFoundException("User with username $username not found")
        } else {
            return user.get()
        }
    }
}