package com.zigzag.jobotaserver.features.authorization.controller

import com.zigzag.jobotaserver.core.rest.JwtSigner
import com.zigzag.jobotaserver.features.authorization.model.AuthResponse
import com.zigzag.jobotaserver.features.authorization.model.UserCredentials
import com.zigzag.jobotaserver.features.user.database.PlatformUserRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/auth")
class AuthController(
    private val userRepository: PlatformUserRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val jwtSigner: JwtSigner
) {
    @PostMapping("/login")
    open fun login(@RequestBody userCredential: UserCredentials): Mono<ResponseEntity<*>> {
        return userRepository.findOneByEmail(userCredential.email)
            .map { user ->
                if (passwordEncoder.matches(userCredential.password, user.password)) {
                     ResponseEntity.ok((AuthResponse(jwtSigner.createJwt(user.id!!)))) as ResponseEntity<Any>
                } else {
                     ResponseEntity.badRequest().body("Error") as ResponseEntity<String>
                }
            }
            .defaultIfEmpty(ResponseEntity.badRequest().body("User not exist"))
    }
}