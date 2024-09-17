package com.learning.oauth

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class CashCardApplicationTests {

    @Autowired
    private val mvc: MockMvc? = null

    @Test
    fun shouldReturnACashCardWhenDataIsSaved() {
        mvc!!.perform(MockMvcRequestBuilders.get("/cashcards/1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(1000))
            .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("John Doe"))
    }
}