package com.zigzag.jobotaserver.integration.mvc

import com.zigzag.jobotaserver.config.DevSecurityConfig
import com.zigzag.jobotaserver.features.user.controller.PlatformUserController
import com.zigzag.jobotaserver.features.user.database.PlatformUser
import com.zigzag.jobotaserver.features.user.database.PlatformUserRepository
import com.zigzag.jobotaserver.features.user.mapper.NewPlatformUserMapperImpl
import com.zigzag.jobotaserver.features.user.mapper.PlatformUserMapper
import com.zigzag.jobotaserver.features.user.mapper.PlatformUserMapperImpl
import com.zigzag.jobotaserver.features.user.service.IPlatformUserService
import org.junit.jupiter.api.Test
import org.mapstruct.factory.Mappers
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration
import org.springframework.boot.autoconfigure.data.mongo.MongoReactiveRepositoriesAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import reactor.core.publisher.Mono

val BASE_URL = "/user";
@ActiveProfiles("test")
@SpringBootTest
//@WebFluxTest(controllers=[PlatformUserController::class])
@AutoConfigureWebTestClient
@Import(value=[DevSecurityConfig::class, NewPlatformUserMapperImpl::class,PlatformUserMapperImpl::class])
    class UserMvcTest {
    @Autowired
    private val webClient: WebTestClient? = null

    @Autowired
    private val platformUserService: IPlatformUserService? = null

    @Autowired
    private val platformUserRepository: PlatformUserRepository? = null

    @Test
    fun testCreateEmployee() {
        val platformUser = PlatformUser(
            firstName = "testName",
            lastName = "lastName",
            email="test1005001@org.com"
        )
        //var userDTO = Mappers.getMapper(PlatformUserMapper::class.java).convertToDto(platformUser);
        //Mockito.`when`(platformUserService!!.create(platformUser)).thenReturn(Mono.just(platformUser));
        webClient!!.post()
            .uri(BASE_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue<Any>(platformUser))
            .exchange()
            .expectStatus().isCreated

        //Mockito.verify(platformUserService, times(1))!!.create(platformUser)
    }

    @Test
    fun testCreateEmployeeDuplicate() {
        val platformUser = PlatformUser(
            firstName = "testName",
            lastName = "lastName",
            email="test1005002@org.com"
        )
        platformUserRepository!!.save(platformUser).block();
        //var userDTO = Mappers.getMapper(PlatformUserMapper::class.java).convertToDto(platformUser);
        //Mockito.`when`(platformUserService!!.create(platformUser)).thenReturn(Mono.just(platformUser));
        webClient!!.post()
            .uri(BASE_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue<Any>(platformUser))
            .exchange()
            .expectStatus().is4xxClientError()

        //Mockito.verify(platformUserService, times(1))!!.create(platformUser)
    }
}