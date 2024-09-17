package com.learning.oauth.service

import com.learning.oauth.dto.cashcard.CreateCashCardDto
import com.learning.oauth.entity.CashCard
import com.learning.oauth.repository.CashCardRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CashCardService(
    private val userService: UserService,
    private val cashCardRepository: CashCardRepository,
) {
    fun getCashCard(id: Long): CashCard {
        val cashCard = cashCardRepository.findById(id)
        if (!cashCard.isPresent) {
            throw EntityNotFoundException("CashCard with id $id not found")
        } else {
            return cashCard.get()
        }
    }

    fun saveCashCard(cashCard: CreateCashCardDto): CashCard {
        val user = userService.getUser(cashCard.userId)

        val cashCardEntity = CashCard(amount = cashCard.amount, user = user)

        return cashCardRepository.save(cashCardEntity)
    }
}