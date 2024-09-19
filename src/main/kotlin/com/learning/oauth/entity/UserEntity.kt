package com.learning.oauth.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    val name: String = "",

    val email: String = "",

    @Column(name = "username", unique = true, nullable = false)
    val username: String = "",
) {
    @OneToMany(mappedBy = "user")
    val cashCards: MutableList<CashCardEntity> = mutableListOf()
}
