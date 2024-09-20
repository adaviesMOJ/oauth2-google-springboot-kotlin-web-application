package com.learning.oauth.dto.cashcard

data class CreateCashCardDto(
    val amount: Long,
    val oauth2Identifier: String,
)