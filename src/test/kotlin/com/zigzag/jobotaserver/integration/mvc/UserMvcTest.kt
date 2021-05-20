package com.zigzag.jobotaserver.integration.mvc

import com.zigzag.jobotaserver.config.DevSecurityConfig
import com.zigzag.jobotaserver.features.user.database.PlatformUser
import com.zigzag.jobotaserver.features.user.database.PlatformUserRepository
import com.zigzag.jobotaserver.helpers.TestHelperUser
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.BasicQuery
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters

/**
 * Basic MVC Test
 * @author  Roman Zinchuk
 */
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureWebTestClient
@WithMockUser
@Import(value=[DevSecurityConfig::class])
class UserMvcTest
@Autowired
constructor
(
    private val webClient: WebTestClient,
    private val platformUserRepository: PlatformUserRepository,
    private val mongoTemplate: MongoTemplate,
    private val testHelperUser: TestHelperUser
) {
    companion object{
        const val BASE_URL = "/user"
    }

    @AfterEach
    fun clearCollection( testInfo:TestInfo) {
        /*Don't drop collection, because it destroy indexes and they won't be recreated*/
        mongoTemplate.remove(BasicQuery("{}"),PlatformUser::class.java)
    }

    @Test
    fun test_create_user() {
        val platformUser = testHelperUser.createTestUser()
        webClient.post()
            .uri(BASE_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue<Any>(platformUser))
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .jsonPath("$.firstName").isEqualTo(platformUser.firstName)
            .jsonPath("$.lastName").isEqualTo(platformUser.lastName)
            .jsonPath("$.email").isEqualTo(platformUser.email)
    }

    @Test
    fun test_not_have_side_effects() {
        val platformUser = testHelperUser.createTestUser()
        webClient.post()
            .uri(BASE_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue<Any>(platformUser))
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .jsonPath("$.firstName").isEqualTo(platformUser.firstName)
            .jsonPath("$.lastName").isEqualTo(platformUser.lastName)
            .jsonPath("$.email").isEqualTo(platformUser.email)
            .jsonPath("$.id").isNotEmpty
    }

    @Test
    fun test_create_employee_duplicate_has_error() {
        val platformUser = testHelperUser.createTestUser()
        platformUserRepository.save(platformUser).block()
        webClient.post()
            .uri(BASE_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue<Any>(platformUser))
            .exchange()
            .expectStatus().is4xxClientError
    }

    @Test
    fun test_get_user_by_id(){
        val platformUser = testHelperUser.createTestUser()
        val createdUser = platformUserRepository.save(platformUser).block()
        webClient.get()
            .uri(BASE_URL + "/" + createdUser.id)
            .exchange()
            .expectStatus()
            .is2xxSuccessful
            .expectBody()
            .jsonPath("$.firstName").isEqualTo(platformUser.firstName)
            .jsonPath("$.lastName").isEqualTo(platformUser.lastName)
            .jsonPath("$.email").isEqualTo(platformUser.email)
    }

    @Test
    fun test_get_all_users(){
        val usersCount = 2
        val users = testHelperUser.createTestUsersList(usersCount)
        platformUserRepository.saveAll(users).blockLast()
        webClient.get()
            .uri(BASE_URL)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBodyList(PlatformUser::class.java)
            .hasSize(usersCount)
    }

    @Test
    fun test_delete_user_by_id(){
        val platformUser = testHelperUser.createTestUser()
        val createdUser = platformUserRepository.save(platformUser).block()
        webClient.delete()
            .uri(BASE_URL + "/" + createdUser.id)
            .exchange()
            .expectStatus().is2xxSuccessful
        Assertions.assertNull(platformUserRepository.findById(createdUser.id).block())
    }

}