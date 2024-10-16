package com.learning.oauth.controller

import com.learning.oauth.annotations.CurrentUser
import com.learning.oauth.dto.cashcard.CashCardDto
import com.learning.oauth.dto.cashcard.CreateCashCardDto
import com.learning.oauth.dto.cashcard.CreateCashCardRequestDto
import com.learning.oauth.entity.CashCardEntity
import com.learning.oauth.service.CashCardService
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PostAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

const val BASE_CASHCARDS: String = "/cashcards"
const val GET_CASHCARD_BY_ID: String = "/{id}"

@RestController
@RequestMapping(BASE_CASHCARDS, name = "Cash Card Controller", produces = [MediaType.APPLICATION_JSON_VALUE])
class CashCardController(
    private val cashCardService: CashCardService,
) {
    @PostMapping
    fun createCashCard(@RequestBody cashCard: CreateCashCardRequestDto, @CurrentUser oauth2Identifier: String): CashCardDto {
        val createCashCardDto = CreateCashCardDto(amount = cashCard.amount, oauth2Identifier = oauth2Identifier)
        val cashCardEntity = cashCardService.saveCashCard(createCashCardDto)

        return CashCardDto(cashCardEntity)
    }

    @GetMapping
    fun getAllCashCardsForUser(@CurrentUser oauth2Identifier: String): List<CashCardDto> {
        return cashCardService.getAllCashCards(oauth2Identifier)
            .map { cashCard: CashCardEntity -> CashCardDto(cashCard) }
    }

    @PostAuthorize("returnObject.oauth2Identifier == authentication.name")
    @GetMapping(GET_CASHCARD_BY_ID)
    fun getCashCard(@PathVariable id: Long): CashCardDto {
        return CashCardDto(cashCardService.getCashCard(id))
    }
}
