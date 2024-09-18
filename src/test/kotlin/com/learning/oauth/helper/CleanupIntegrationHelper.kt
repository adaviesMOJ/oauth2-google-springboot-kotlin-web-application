package com.learning.oauth.helper

import com.learning.oauth.repository.CashCardRepository
import com.learning.oauth.repository.UserRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation.REQUIRES_NEW
import org.springframework.transaction.annotation.Transactional


@Component
class CleanupIntegrationHelper(
    private val cashCardRepository: CashCardRepository,
    private val userRepository: UserRepository,
) {

    @Transactional(propagation = REQUIRES_NEW)
    fun deleteAll() {
        cashCardRepository.deleteAll()
        userRepository.deleteAll()
    }
}