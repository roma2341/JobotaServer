package com.zigzag.jobotaserver.features.user.controller

import com.zigzag.jobotaserver.core.ICrudRestController
import com.zigzag.jobotaserver.features.user.database.PlatformUserRepository
import com.zigzag.jobotaserver.features.user.dto.NewPlatformUserDto
import com.zigzag.jobotaserver.features.user.dto.PlatformUserDto
import com.zigzag.jobotaserver.features.user.mapper.NewPlatformUserMapper
import com.zigzag.jobotaserver.features.user.mapper.PlatformUserMapper
import com.zigzag.jobotaserver.features.user.service.IPlatformUserService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 *
 * @author  Roman Zinchuk
 */
@RestController
@RequestMapping("user")
class PlatformUserController(val userService: IPlatformUserService,
                             val userRepository: PlatformUserRepository,
                             val userMapper: PlatformUserMapper,
                             val newUserMapper: NewPlatformUserMapper) : ICrudRestController<PlatformUserDto,NewPlatformUserDto> {


    override fun all(): Flux<PlatformUserDto> {
        return userRepository.findAll().map(userMapper::convertToDto)
    }
    override fun get(entityId:String):Mono<PlatformUserDto>{
        return userRepository
            .findById(entityId)
            .map(userMapper::convertToDto)
    }

    override fun create(dto: NewPlatformUserDto): Mono<PlatformUserDto> {
        val entity = newUserMapper.convertToModel(dto)
        return userService
            .create(entity)
            .map(userMapper::convertToDto)
    }

    override fun delete(entityId: String):Mono<Void>{
        return userRepository.deleteById(entityId)
    }


}
