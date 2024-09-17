package com.learning.oauth.repository

import com.learning.oauth.entity.CashCard
import com.learning.oauth.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CashCardRepository : JpaRepository<CashCard, Long>