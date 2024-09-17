package com.learning.oauth.dto.cashcard

import com.learning.oauth.entity.CashCard

data class CashCardDto(
    val amount: Long,
    val userName: String,
) {
    constructor(cashCardEntity: CashCard): this(
        amount = cashCardEntity.amount,
        userName = cashCardEntity.user.name,
    )
}