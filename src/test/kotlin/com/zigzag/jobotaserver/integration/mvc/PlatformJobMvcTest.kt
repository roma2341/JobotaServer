package com.zigzag.jobotaserver.integration.mvc

import com.zigzag.jobotaserver.config.DevSecurityConfig
import com.zigzag.jobotaserver.features.job.database.JobRepository
import com.zigzag.jobotaserver.features.job.database.PlatformJob
import com.zigzag.jobotaserver.features.job.service.JobService
import com.zigzag.jobotaserver.features.user.database.PlatformUser
import com.zigzag.jobotaserver.features.user.database.PlatformUserRepository
import com.zigzag.jobotaserver.helpers.TestHelperJob
import com.zigzag.jobotaserver.helpers.TestHelperUser
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
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
@WithMockUser(TestHelperUser.CURRENT_USER_ID)
@Import(value=[DevSecurityConfig::class])
class PlatformJobMvcTest
@Autowired
constructor
    (
    private val webClient: WebTestClient,
    private val jobRepository: JobRepository,
    private val jobService: JobService,
    private val mongoTemplate: MongoTemplate,
    private val userRepository: PlatformUserRepository,
    private val testHelperUser: TestHelperUser,
    private val testHelperJob: TestHelperJob
){
    companion object {
        const val BASE_URL = "/job"
    }

    @BeforeEach
    fun initTestUser(){
        userRepository.save(testHelperUser.createCurrentUser()).block()
    }

    @AfterEach
    fun clearCollection() {
        /*Don't drop collection, because it destroy indexes and they won't be recreated*/
        mongoTemplate.remove(BasicQuery("{}"), PlatformJob::class.java)
        mongoTemplate.remove(BasicQuery("{}"), PlatformUser::class.java)
    }

    @Test
    fun test_create_job() {
        val user = testHelperUser.createTestUser()
        val job = PlatformJob(
            name = "testJob",
            description = "description",
        )
        webClient.post()
            .uri(BASE_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue<Any>(job))
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .jsonPath("$.name").isEqualTo(job.name)
            .jsonPath("$.description").isEqualTo(job.description)
            .jsonPath("$.author.id").isEqualTo(TestHelperUser.CURRENT_USER_ID)
    }

    @Test
    fun test_get_job_by_id() {
        val job = jobService.create(PlatformJob(name="test-job",description = "test description")).block()
        webClient.get()
            .uri("${BASE_URL}/${job.id}")
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody()
            .jsonPath("$.name").isEqualTo(job.name)
            .jsonPath("$.description").isEqualTo(job.description)
            .jsonPath("$.author.id").isEqualTo(TestHelperUser.CURRENT_USER_ID)
    }

    @Test
    fun test_get_all_jobs() {
        val jobs = listOf(PlatformJob(name="test-job",description = "test description"),
            PlatformJob(name="test-job-2",description = "test description-2"))
        jobs.forEach{jobService.create(it).block()}
        webClient.get()
            .uri("${BASE_URL}/")
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBodyList(PlatformJob::class.java)
            .hasSize(jobs.size)
    }

    @Test
    fun test_delete_by_id(){
        val job = testHelperJob.createTestJob()
        val createdJob = jobRepository.save(job).block()
        webClient.delete()
            .uri(BASE_URL + "/" + createdJob.id)
            .exchange()
            .expectStatus().is2xxSuccessful
        Assertions.assertNull(jobRepository.findById(createdJob.id).block())
    }
}