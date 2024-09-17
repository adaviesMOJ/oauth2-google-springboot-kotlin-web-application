package com.learning.oauth.service

import com.learning.oauth.dto.user.CreateUserDto
import com.learning.oauth.entity.User
import com.learning.oauth.repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
) {
    fun saveUser(user: CreateUserDto): User {
        val userEntity = User(name = user.name, email = user.email)
        return userRepository.save(userEntity)
    }

    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    fun getUser(id: Long): User {
        val user = userRepository.findById(id)
        if (!user.isPresent) {
            throw EntityNotFoundException("User with id $id not found")
        } else {
            return user.get()
        }
    }
}