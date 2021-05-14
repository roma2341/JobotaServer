package com.zigzag.jobotaserver.integration.mvc

import com.fasterxml.jackson.databind.ObjectMapper
import com.zigzag.jobotaserver.config.DevSecurityConfig
import com.zigzag.jobotaserver.features.user.database.PlatformUser
import com.zigzag.jobotaserver.features.user.database.PlatformUserRepository
import com.zigzag.jobotaserver.features.user.mapper.NewPlatformUserMapperImpl
import com.zigzag.jobotaserver.features.user.mapper.PlatformUserMapperImpl
import com.zigzag.jobotaserver.features.user.service.IPlatformUserService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.mockito.verification.After
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.BasicQuery
import org.springframework.data.mongodb.core.query.CriteriaDefinition
import org.springframework.data.mongodb.core.query.TextCriteria
import org.springframework.data.mongodb.core.query.TextQuery
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import java.util.*

/**
 * Basic MVC Test
 * @author  Roman Zinchuk
 */
val BASE_URL = "/user";
@ActiveProfiles("test")
@SpringBootTest
//@WebFluxTest(controllers=[PlatformUserController::class])
@AutoConfigureWebTestClient
@WithMockUser()//username = "t@t.t",password = "p")
@Import(value=[DevSecurityConfig::class, NewPlatformUserMapperImpl::class,PlatformUserMapperImpl::class])
class UserMvcTest
@Autowired
constructor
(
 private val webClient: WebTestClient,
 private val platformUserRepository: PlatformUserRepository,
 private val mongoTemplate: MongoTemplate,
 private val objectMapper: ObjectMapper
) {

    @AfterEach
    fun clearCollection() {
        /*Don't drop collection, because it destroy indexes and they won't be recreated*/
        mongoTemplate.remove(BasicQuery("{}"),PlatformUser::class.java);
    }

    @Test
    fun test_create_user() {
        val platformUser = PlatformUser(
            firstName = "testName",
            lastName = "lastName",
            email="test@org.com",
            password = "testpass"
        )

        webClient.post()
            .uri(BASE_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue<Any>(platformUser))
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .json("{\n  \"firstName\": \"testName\",\n  \"lastName\": \"lastName\"," +
                    "\n  \"email\": \"test@org.com\"\n}" +
                    "")
    }

    @Test
    fun test_not_have_side_effects() {
        val platformUser = PlatformUser(
            firstName = "testName",
            lastName = "lastName",
            email="test@org.com",
            password = "testpass"
        )
        webClient.post()
            .uri(BASE_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue<Any>(platformUser))
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .json("{\n  \"firstName\": \"testName\",\n  \"lastName\": \"lastName\"," +
                    "\n  \"email\": \"test@org.com\"\n}" +
                    "")
            .jsonPath("$.id").isNotEmpty()
    }

    @Test
    fun test_create_employee_duplicate_has_error() {
        val platformUser = PlatformUser(
            firstName = "testName",
            lastName = "lastName",
            email="test@org.com",
            password = "testpass"
        )
        platformUserRepository.save(platformUser).block()
        webClient.post()
            .uri(BASE_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue<Any>(platformUser))
            .exchange()
            .expectStatus().is4xxClientError()
    }

    @Test
    fun test_get_user_by_id(){
        val platformUser = PlatformUser(
            firstName = "testName",
            lastName = "lastName",
            email="test@org.com",
            password = "testpass"
        )
        val createdUser = platformUserRepository.save(platformUser).block();
        webClient.get()
            .uri(BASE_URL + "/" + createdUser.id)
            .exchange()
            .expectStatus()
            .is2xxSuccessful()
            .expectBody()
            .json("{\n  \"firstName\": \"testName\",\n  \"lastName\": \"lastName\"," +
            "\n  \"email\": \"test@org.com\"\n}" +
            "")
    }

    @Test
    fun test_get_all_users(){
        val platformUser1 = PlatformUser(
            firstName = "testName",
            lastName = "lastName",
            email="test@org.com",
            password = "testpass"
        )
        val platformUser2 = PlatformUser(
            firstName = "testName",
            lastName = "lastName",
            email="test2@org.com",
            password = "testpass"
        )
        platformUserRepository.saveAll(listOf(platformUser1,platformUser2)).blockLast()
        webClient.get()
            .uri(BASE_URL)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBodyList(PlatformUser::class.java)
            .hasSize(2)
    }

    @Test
    fun test_delete_user_by_id(){
        val createdUser = platformUserRepository.save(PlatformUser(email="test@org.com")).block()
        webClient.delete()
            .uri(BASE_URL + "/" + createdUser.id)
            .exchange()
        Assertions.assertNotNull(platformUserRepository.findById(createdUser.id).block());
    }

}