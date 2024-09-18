package com.learning.oauth.dto.cashcard

import com.learning.oauth.entity.CashCardEntity

data class CashCardDto(
    val id: Int,
    val amount: Long,
    val username: String,
) {
    constructor(cashCardEntity: CashCardEntity): this(
        id = cashCardEntity.id,
        amount = cashCardEntity.amount,
        username = cashCardEntity.user.username,
    )
}