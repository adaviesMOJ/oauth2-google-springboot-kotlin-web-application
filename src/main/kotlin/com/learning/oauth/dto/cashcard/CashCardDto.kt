package com.learning.oauth.dto.cashcard

import com.learning.oauth.entity.CashCardEntity

data class CashCardDto(
    val amount: Long,
    val userName: String,
) {
    constructor(cashCardEntity: CashCardEntity): this(
        amount = cashCardEntity.amount,
        userName = cashCardEntity.user.name,
    )
}