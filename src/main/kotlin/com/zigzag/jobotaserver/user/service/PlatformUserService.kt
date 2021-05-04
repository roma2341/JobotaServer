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
class PlatformUserService(val userRepository: PlatformUserRepository,val newUserMapper: NewPlatformUserMapper) {
    fun register(user: NewPlatformUserDto): Mono<PlatformUser> {
        var model = newUserMapper.convertToModel(user);
        return userRepository.save(model);
    }
}