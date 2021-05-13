package com.zigzag.jobotaserver.features.user.service

import com.zigzag.jobotaserver.features.user.database.PlatformUser
import com.zigzag.jobotaserver.features.user.database.PlatformUserRepository
import com.zigzag.jobotaserver.features.user.mapper.NewPlatformUserMapper
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 *
 * @author  Roman Zinchuk
 */
@Service
class PlatformUserService(val userRepository: PlatformUserRepository,val newUserMapper: NewPlatformUserMapper) : IPlatformUserService {
    override fun all(): Flux<PlatformUser> {
        return userRepository.findAll()
    }

    override fun get(modelId: String): Mono<PlatformUser> {
        return userRepository.findById(modelId)
    }

    override fun create(model: PlatformUser): Mono<PlatformUser> {
        return userRepository.save(model)
    }

    override fun delete(userId: String): Mono<Void> {
        return userRepository.deleteById(userId)
    }

}