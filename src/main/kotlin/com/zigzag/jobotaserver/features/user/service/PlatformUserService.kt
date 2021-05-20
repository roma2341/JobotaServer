package com.zigzag.jobotaserver.features.user.service

import com.zigzag.jobotaserver.features.user.database.PlatformUser
import com.zigzag.jobotaserver.features.user.database.PlatformUserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 *
 * @author  Roman Zinchuk
 */
@Service
class PlatformUserService(val userRepository: PlatformUserRepository,
                          val passwordEncoder: BCryptPasswordEncoder) : IPlatformUserService {
    override fun all(): Flux<PlatformUser> {
        return userRepository.findAll()
    }

    override fun get(modelId: String): Mono<PlatformUser> {
        return userRepository.findById(modelId)
    }

    override fun create(model: PlatformUser): Mono<PlatformUser> {
        model.password = passwordEncoder.encode(model.password)
        return userRepository.save(model)
    }

    override fun delete(userId: String): Mono<Void> {
        return userRepository.deleteById(userId)
    }

}