package com.learning.oauth.dto.cashcard

data class CreateCashCardDto(
    val amount: Long,
    val userName: String,
    val userId: Long,
)