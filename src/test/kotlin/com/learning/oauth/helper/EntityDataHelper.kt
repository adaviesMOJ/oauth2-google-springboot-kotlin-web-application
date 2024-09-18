package com.learning.oauth.helper

import com.learning.oauth.entity.CashCardEntity
import com.learning.oauth.entity.UserEntity
import com.learning.oauth.repository.CashCardRepository
import com.learning.oauth.repository.UserRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional


@Component
@Transactional
class EntityDataHelper(
    private val cashCardRepository: CashCardRepository,
    private val userRepository: UserRepository,
) {

    fun createUser(userEntity: UserEntity): UserEntity {
        return userRepository.save(userEntity)
    }

    fun createCashCard(cashCardEntity: CashCardEntity): CashCardEntity {
        return cashCardRepository.save(cashCardEntity)
    }
}