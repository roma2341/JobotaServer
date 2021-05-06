package com.zigzag.jobotaserver.user.service

import com.zigzag.jobotaserver.user.database.PlatformUser
import com.zigzag.jobotaserver.user.database.PlatformUserRepository
import com.zigzag.jobotaserver.user.dto.NewPlatformUserDto
import com.zigzag.jobotaserver.user.mapper.NewPlatformUserMapper
import com.zigzag.jobotaserver.user.mapper.PlatformUserMapper
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class PlatformUserService(val userRepository: PlatformUserRepository,val newUserMapper: NewPlatformUserMapper) : IPlatformUserService {
    override fun all(): Flux<PlatformUser> {
        return userRepository.findAll();
    }

    override fun get(modelId: String): Mono<PlatformUser> {
        return userRepository.findById(modelId);
    }

    override fun create(model: PlatformUser): Mono<PlatformUser> {
        return userRepository.save(model);
    }

    override fun delete(userId: String): Mono<Void> {
        return userRepository.deleteById(userId);
    }

}