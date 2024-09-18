package com.learning.oauth.service

import com.learning.oauth.dto.cashcard.CreateCashCardDto
import com.learning.oauth.dto.cashcard.CreateCashCardRequestDto
import com.learning.oauth.entity.CashCardEntity
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
    fun getCashCard(id: Long): CashCardEntity {
        val cashCard = cashCardRepository.findById(id)
        if (!cashCard.isPresent) {
            throw EntityNotFoundException("CashCard with id $id not found")
        } else {
            return cashCard.get()
        }
    }

    fun getAllCashCards(username: String): List<CashCardEntity> {
        return cashCardRepository.findAllCashCardsByUsername(username)
    }

    fun saveCashCard(cashCard: CreateCashCardDto): CashCardEntity {
        val user = userService.getUserByUsername(cashCard.username)

        val cashCardEntity = CashCardEntity(amount = cashCard.amount, user = user)

        return cashCardRepository.save(cashCardEntity)
    }
}