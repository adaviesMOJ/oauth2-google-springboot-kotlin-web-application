package com.learning.oauth.controller

import com.learning.oauth.dto.cashcard.CashCardDto
import com.learning.oauth.dto.cashcard.CreateCashCardDto
import com.learning.oauth.service.CashCardService
import org.springframework.http.MediaType
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
    fun saveCashCard(@RequestBody cashCard: CreateCashCardDto): CashCardDto {
        return CashCardDto(cashCardService.saveCashCard(cashCard))
    }

    @GetMapping(GET_CASHCARD_BY_ID)
    fun getCashCard(@PathVariable id: Long): CashCardDto {
        return CashCardDto(cashCardService.getCashCard(id))
    }
}