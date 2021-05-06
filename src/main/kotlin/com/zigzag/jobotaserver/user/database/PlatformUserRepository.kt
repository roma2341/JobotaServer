package com.zigzag.jobotaserver.user.database

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface PlatformUserRepository : ReactiveMongoRepository<PlatformUser, String> {
    fun findByEmail(email:String) : Flux<PlatformUser>
}
