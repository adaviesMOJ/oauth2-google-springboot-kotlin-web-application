package com.learning.oauth.repository

import com.learning.oauth.entity.CashCardEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CashCardRepository : JpaRepository<CashCardEntity, Long>