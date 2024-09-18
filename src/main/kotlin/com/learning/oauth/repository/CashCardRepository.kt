package com.learning.oauth.repository

import com.learning.oauth.entity.CashCardEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CashCardRepository : JpaRepository<CashCardEntity, Long> {

    @Query("SELECT * FROM cashcards c WHERE c.username = :username", nativeQuery = true)
    fun findAllCashCardsByUsername(username: String): List<CashCardEntity>
}