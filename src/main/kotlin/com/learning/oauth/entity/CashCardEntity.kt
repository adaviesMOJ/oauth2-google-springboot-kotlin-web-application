package com.learning.oauth.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "cashcards")
class CashCardEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Int = 0,

    @Column(name = "amount", nullable = false)
    val amount: Long,

    @ManyToOne
    @JoinColumn(name = "oauth2_identifier", referencedColumnName = "oauth2_identifier", nullable = false)
    val user: UserEntity,
)
