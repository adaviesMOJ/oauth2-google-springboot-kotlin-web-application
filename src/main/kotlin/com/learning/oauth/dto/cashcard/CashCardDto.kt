package com.learning.oauth.dto.cashcard

import com.learning.oauth.entity.CashCardEntity

data class CashCardDto(
    val id: Int,
    val amount: Long,
    val oauth2Identifier: String,
) {
    constructor(cashCardEntity: CashCardEntity): this(
        id = cashCardEntity.id,
        amount = cashCardEntity.amount,
        oauth2Identifier = cashCardEntity.user.oauth2Identifier,
    )
}