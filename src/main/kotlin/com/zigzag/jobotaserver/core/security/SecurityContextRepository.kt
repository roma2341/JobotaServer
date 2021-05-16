package com.zigzag.jobotaserver.core.security

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono


@Component
class SecurityContextRepository @Autowired constructor(
    private val authenticationManager: AppAuthenticationManager
) : ServerSecurityContextRepository {
    private val logger: Logger = LoggerFactory.getLogger(SecurityContextRepository::class.java)

    private val TOKEN_PREFIX = "Bearer "

    override fun save(swe: ServerWebExchange?, sc: SecurityContext?): Mono<Void>? {
        throw UnsupportedOperationException("Not supported yet.")
    }

    override fun load(swe: ServerWebExchange): Mono<SecurityContext>? {
        val request: ServerHttpRequest = swe.request
        val authHeader: String? = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION)
        if(authHeader == null) {
            return Mono.empty();
        }
        var authToken: String? = null
        if (authHeader != null && authHeader.startsWith(TOKEN_PREFIX)) {
            authToken = authHeader.replace(TOKEN_PREFIX, "")
        } else {
            logger.warn("couldn't find bearer string, will ignore the header.")
        }
        if(authToken == null){
            return  Mono.empty();
        }
        val auth: Authentication = UsernamePasswordAuthenticationToken(authToken, authToken)
        return authenticationManager
            .authenticate(auth)
            .map{authenticatedUser -> SecurityContextImpl(authenticatedUser)}
    }
}