package com.learning.oauth.repository

import com.learning.oauth.entity.CashCardEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CashCardRepository : JpaRepository<CashCardEntity, Long> {

    @Query("SELECT c FROM CashCardEntity c WHERE c.user.oauth2Identifier = :oauth2Identifier")
    fun findAllCashCardsByOauth2Identifier(oauth2Identifier: String): List<CashCardEntity>
}