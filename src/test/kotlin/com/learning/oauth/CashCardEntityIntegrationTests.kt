package com.learning.oauth

import com.learning.oauth.dto.cashcard.CashCardDto
import com.learning.oauth.dto.cashcard.CreateCashCardRequestDto
import com.learning.oauth.entity.CashCardEntity
import com.learning.oauth.entity.UserEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "test1")
class CashCardEntityIntegrationTests : IntegrationTestBase() {

    @Test
    fun shouldReturnACashCardWhenRequested() {
        val tempUser =  entityDataHelper.createUser(UserEntity(email = "test@test.test", name = "Test User", username = "test1"))
        val tempCashCardId =  entityDataHelper.createCashCard(CashCardEntity(amount = 1000, user = tempUser)).id

        mockMvc.perform(get("/cashcards/$tempCashCardId"))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(1000))
            .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("test1"))
    }

    @Test
    fun shouldCreateCashCardWhenRequested() {
        entityDataHelper.createUser(UserEntity(email = "test@test.test", name = "Test User", username = "test1"))

        val requestBody = CreateCashCardRequestDto(
            amount = 1000,
        )

        mockMvc.perform(
            post("/cashcards")
                .with(csrf())
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody))
        ).andExpect(status().isOk)
    }

    @Test
    fun shouldReturnAllCashCardsForUserWhenRequested() {
        // Given multiple users exist who all have cash cards.
        val user1 = entityDataHelper.createUser(UserEntity(email = "test@test.com", name = "Test One", username = "test1"))
        val user2 = entityDataHelper.createUser(UserEntity(email = "anotherEmail@test.com", name = "Test Two", username = "test2"))

        entityDataHelper.createCashCard(CashCardEntity(amount = 1000, user = user1))
        entityDataHelper.createCashCard(CashCardEntity(amount = 1000, user = user2))

        // When I call to get all cash cards.
        val mvcResult = mockMvc.perform(
            get("/cashcards"))
                .andExpect(status().isOk())
                .andReturn()

        // Then only the authorised user's cashcards are returned.
        val jsonResponse = mvcResult.response.contentAsString
        val cashCardDtos = objectMapper.readValue(jsonResponse, Array<CashCardDto>::class.java)

        assertEquals(cashCardDtos.size, 1)
        assertEquals(cashCardDtos[0].username, "test1")
    }
}