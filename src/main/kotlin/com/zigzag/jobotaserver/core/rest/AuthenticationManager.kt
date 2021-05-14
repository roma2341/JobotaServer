package com.zigzag.jobotaserver.core.rest

import io.jsonwebtoken.Claims
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.stream.Collectors


@Component
class AppAuthenticationManager (
    private val jwtSigner: JwtSigner
    ): ReactiveAuthenticationManager {
    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        val authToken: String = authentication.getCredentials().toString()
        val email: String?
        email = try {
            jwtSigner.getEmailFromToken(authToken)
        } catch (e: Exception) {
            null
        }
        return if (email != null && !jwtSigner.isTokenExpired(authToken)) {
            val claims: Claims = jwtSigner.getAllClaimsFromToken(authToken)
            val roles = claims.get("authorities") as List<String>;
            val authorities = roles.stream().map { role: String ->
                SimpleGrantedAuthority(
                    role
                )
            }.collect(Collectors.toList())
            val auth = UsernamePasswordAuthenticationToken(email, email, authorities)
            SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(email, authorities)
            Mono.just(auth)
        } else { Mono.empty() }
    }
}
