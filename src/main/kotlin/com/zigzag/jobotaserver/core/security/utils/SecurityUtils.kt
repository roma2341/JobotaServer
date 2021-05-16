package com.zigzag.jobotaserver.core.security.utils

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class SecurityUtils {
    fun getCurrentUserId(): Mono<String> {
        return ReactiveSecurityContextHolder.getContext().map {
            val userDetails = it.getAuthentication() as UsernamePasswordAuthenticationToken
            userDetails.name
        }
    }

}