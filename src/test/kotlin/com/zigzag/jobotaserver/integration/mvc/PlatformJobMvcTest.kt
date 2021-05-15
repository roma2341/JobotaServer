package com.zigzag.jobotaserver.integration.mvc

import com.fasterxml.jackson.databind.ObjectMapper
import com.zigzag.jobotaserver.config.DevSecurityConfig
import com.zigzag.jobotaserver.features.job.database.PlatformJob
import com.zigzag.jobotaserver.features.job.database.JobRepository
import com.zigzag.jobotaserver.features.user.database.PlatformUser
import com.zigzag.jobotaserver.features.user.database.PlatformUserRepository
import com.zigzag.jobotaserver.helpers.TestHelperUser
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
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

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureWebTestClient
@WithMockUser()
@Import(value=[DevSecurityConfig::class])
class PlatformJobMvcTest
@Autowired
constructor
    (
    private val webClient: WebTestClient,
    private val jobRepository: JobRepository,
    private val mongoTemplate: MongoTemplate,
    private val userRepository: PlatformUserRepository,
    private val testHelperUser: TestHelperUser
){
    val BASE_URL = "/job";

    @AfterEach
    fun clearCollection() {
        /*Don't drop collection, because it destroy indexes and they won't be recreated*/
        mongoTemplate.remove(BasicQuery("{}"), PlatformJob::class.java)
        mongoTemplate.remove(BasicQuery("{}"), PlatformUser::class.java)
    }

    @Test
    fun test_create_job() {
        val user = testHelperUser.createTestUser();
        val createdUser = userRepository.save(user).block();
        val job = PlatformJob(
            name = "testJob",
            description = "description",
            author = createdUser
        )
        webClient.post()
            .uri(BASE_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue<Any>(job))
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .jsonPath("$.name").isEqualTo("testJob")
            .jsonPath("$.description").isEqualTo("description")
            //.jsonPath("$.author").isEqualTo(user.id)
    }

}