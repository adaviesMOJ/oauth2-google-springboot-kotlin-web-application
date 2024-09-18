package com.learning.oauth

import com.fasterxml.jackson.databind.ObjectMapper
import com.learning.oauth.helper.CleanupIntegrationHelper
import com.learning.oauth.helper.EntityDataHelper
import org.junit.jupiter.api.AfterEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc


@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
abstract class IntegrationTestBase {

    @Autowired
    protected lateinit var mockMvc: MockMvc

    @Autowired
    protected lateinit var objectMapper: ObjectMapper

    @Autowired
    protected lateinit var entityDataHelper: EntityDataHelper

    @Autowired
    protected lateinit var cleanupIntegrationHelper: CleanupIntegrationHelper

    @AfterEach
    internal fun deleteAll() {
        cleanupIntegrationHelper.deleteAll()
    }
}