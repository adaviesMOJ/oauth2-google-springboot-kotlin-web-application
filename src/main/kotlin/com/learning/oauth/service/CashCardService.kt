package com.learning.oauth.service

import com.learning.oauth.dto.cashcard.CreateCashCardDto
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

    fun getAllCashCards(oauth2Identifier: String): List<CashCardEntity> {
        return cashCardRepository.findAllCashCardsByOauth2Identifier(oauth2Identifier)
    }

    fun saveCashCard(cashCard: CreateCashCardDto): CashCardEntity {
        val user = userService.getUserByOauth2Identifier(cashCard.oauth2Identifier)

        val cashCardEntity = CashCardEntity(amount = cashCard.amount, user = user)

        return cashCardRepository.save(cashCardEntity)
    }
}