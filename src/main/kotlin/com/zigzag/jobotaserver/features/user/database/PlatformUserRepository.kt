package com.zigzag.jobotaserver.features.user.database

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

/**
 *
 * @author  Roman Zinchuk
 */
@Repository
interface PlatformUserRepository : ReactiveMongoRepository<PlatformUser, String> {
    fun findByEmail(email:String) : Flux<PlatformUser>
}
