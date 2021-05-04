package com.zigzag.jobotaserver.user.controller;

import com.zigzag.jobotaserver.user.database.PlatformUser;
import com.zigzag.jobotaserver.user.database.PlatformUserRepository;
import com.zigzag.jobotaserver.user.dto.NewPlatformUserDto
import com.zigzag.jobotaserver.user.service.PlatformUserService
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono

@RestController
@RequestMapping("user")
class PlatformUserController(val userService: PlatformUserService, val userRepository: PlatformUserRepository) {

    @GetMapping("/all")
    fun all():Flux<PlatformUser>{
        return userRepository.findAll();
    }

    @PostMapping()
    fun register(newUser: NewPlatformUserDto): Mono<PlatformUser> {
        return userService.register(newUser);
    }

}
