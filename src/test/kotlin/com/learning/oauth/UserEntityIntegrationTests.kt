package com.learning.oauth

import com.learning.oauth.dto.user.CreateUserDto
import com.learning.oauth.dto.user.UserDto
import com.learning.oauth.entity.UserEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@SpringBootTest
@WithMockUser
class UserEntityIntegrationTests : IntegrationTestBase() {

    @Test
    fun shouldReturnAUserWhenRequested() {
        val tempUserId =  entityDataHelper.createUser(UserEntity(email = "test@test.test", name = "Test User", username = "test1")).id

        mockMvc.perform(get("/users/$tempUserId"))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test User"))
    }

    @Test
    fun shouldCreateUserWhenRequested() {
        val requestBody = CreateUserDto(
            name = "Test User",
            email = "test@test.test",
            username = "test1"
        )

        val mvcResult = mockMvc.perform(
            post("/users")
                .with(csrf())
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody))
        ).andExpect(status().isOk).andReturn()

        val jsonResponse = mvcResult.response.contentAsString

        val userDto = objectMapper.readValue(jsonResponse, UserDto::class.java)

        assertEquals("test@test.test", userDto.email)
        assertEquals("Test User", userDto.name)
    }
}