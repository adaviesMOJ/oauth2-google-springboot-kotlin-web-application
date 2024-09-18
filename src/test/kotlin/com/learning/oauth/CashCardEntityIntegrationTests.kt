package com.learning.oauth

import com.learning.oauth.dto.cashcard.CashCardDto
import com.learning.oauth.dto.cashcard.CreateCashCardDto
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
        val tempUsername =  entityDataHelper.createUser(UserEntity(email = "test@test.test", name = "Test User", username = "test1")).username

        val requestBody = CreateCashCardDto(
            amount = 1000,
            username = tempUsername
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
        val firstUsername =  entityDataHelper.createUser(UserEntity(email = "test@test.test", name = "Test One", username = "test1")).username
        val secondUsername =  entityDataHelper.createUser(UserEntity(email = "tes2@test.test", name = "Test Two", username = "test2")).username

        val firstCashCardRequest = CreateCashCardDto(
            amount = 1000,
            username = firstUsername
        )
        mockMvc.perform(
            post("/cashcards")
                .with(csrf())
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(firstCashCardRequest))
        ).andExpect(status().isOk)

        val secondCashCardRequest = CreateCashCardDto(
            amount = 150,
            username = secondUsername
        )

        mockMvc.perform(
            post("/cashcards")
                .with(csrf())
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(secondCashCardRequest))
        ).andExpect(status().isOk)

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