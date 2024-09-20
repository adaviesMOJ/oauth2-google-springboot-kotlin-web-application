package com.learning.oauth.security

import com.learning.oauth.IntegrationTestBase
import com.learning.oauth.entity.CashCardEntity
import com.learning.oauth.entity.UserEntity
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import org.hamcrest.Matchers.containsInAnyOrder
import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.time.Instant
import java.util.function.Consumer

@SpringBootTest
@AutoConfigureMockMvc
class SecurityTests : IntegrationTestBase() {
    @Autowired
    private lateinit var jwtEncoder: JwtEncoder

    private fun mint(consumer: Consumer<JwtClaimsSet.Builder> = Consumer { }): String {
        val builder = JwtClaimsSet.builder()
            .issuedAt(Instant.now())
            .expiresAt(Instant.now().plusSeconds(100000))
            .subject("test1")
            .issuer("http://localhost:9000")
            .audience(mutableListOf("cashcard-client"))
            .claim("scp", mutableListOf("cashcard:read", "cashcard:write"))
        consumer.accept(builder)
        val parameters = JwtEncoderParameters.from(builder.build())
        return jwtEncoder.encode(parameters).tokenValue
    }

    @TestConfiguration
    internal class TestJwtConfiguration {
        @Bean
        fun jwtEncoder(
            @Value("classpath:authz.pub") pub: RSAPublicKey?,
            @Value("classpath:authz.pem") pem: RSAPrivateKey?
        ): JwtEncoder {
            val key = RSAKey.Builder(pub).privateKey(pem).build()
            return NimbusJwtEncoder(ImmutableJWKSet(JWKSet(key)))
        }
    }

    @Test
    fun shouldRequireValidTokens() {
        val tempUser =  entityDataHelper.createUser(UserEntity(email = "test@test.test", name = "Test User", username = "test1"))
        val tempCashCardId =  entityDataHelper.createCashCard(CashCardEntity(amount = 1000, user = tempUser)).id

        val token = mint()
        mockMvc.perform(
            get("/cashcards/$tempCashCardId")
                .header("Authorization", "Bearer $token")
        ).andExpect(status().isOk())
    }

    @Test
    fun shouldNotAllowTokensWithAnInvalidAudience() {
        val tempUser =  entityDataHelper.createUser(UserEntity(email = "test@test.test", name = "Test User", username = "test1"))
        val tempCashCardId =  entityDataHelper.createCashCard(CashCardEntity(amount = 1000, user = tempUser)).id

        val token = mint { claims -> claims.audience(listOf("https://wrong")) }

        mockMvc.perform(
            get("/cashcards/$tempCashCardId")
                .header("Authorization", "Bearer $token")
        ).andExpect(status().isUnauthorized())
         .andExpect(header().string("WWW-Authenticate", containsString("aud claim is not valid")))
    }

    @Test
    fun shouldNotAllowTokensThatAreExpired() {
        val tempUser =  entityDataHelper.createUser(UserEntity(email = "test@test.test", name = "Test User", username = "test1"))
        val tempCashCardId =  entityDataHelper.createCashCard(CashCardEntity(amount = 1000, user = tempUser)).id

        val token = mint { claims ->
            claims
                .issuedAt(Instant.now().minusSeconds(3600))
                .expiresAt(Instant.now().minusSeconds(3599))
        }
        mockMvc.perform(
            get("/cashcards/$tempCashCardId")
                .header("Authorization", "Bearer $token")
        ).andExpect(status().isUnauthorized())
         .andExpect(header().string("WWW-Authenticate", containsString("Jwt expired")))
    }

    @Test
    fun shouldShowAllTokenValidationErrorsWhenMultipleErrorsPresent() {
        val tempUser =  entityDataHelper.createUser(UserEntity(email = "test@test.test", name = "Test User", username = "test1"))
        entityDataHelper.createCashCard(CashCardEntity(amount = 1000, user = tempUser))

        val expired = mint { claims ->
            claims
                .audience(listOf("https://wrong"))
                .issuedAt(Instant.now().minusSeconds(3600))
                .expiresAt(Instant.now().minusSeconds(3599))
        }
        mockMvc.perform(get("/cashcards").header("Authorization", "Bearer $expired"))
            .andExpect(status().isUnauthorized())
            .andExpect(header().exists("WWW-Authenticate"))
            .andExpect(
                jsonPath("$.errors..description").value(
                    containsInAnyOrder(containsString("Jwt expired"), containsString("aud claim is not valid"))
                )
            )
    }
}
