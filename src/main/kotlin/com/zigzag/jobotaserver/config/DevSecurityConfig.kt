package com.zigzag.jobotaserver.config

import com.zigzag.jobotaserver.core.security.AppAuthenticationManager
import com.zigzag.jobotaserver.core.security.SecurityContextRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono


/**
 * Dev config without authentication applied to tests and dev
 * @author  Roman Zinchuk
 */
@Profile(value=["dev","test"])
@Configuration
@EnableWebFluxSecurity
class DevSecurityConfig {
     /*@Bean
     fun securityWebFilterChain(http:ServerHttpSecurity): SecurityWebFilterChain {
        return http
                 .csrf().disable()
                .authorizeExchange()
                .anyExchange()
                .permitAll()
                .and()
                .build()
     }*/
     @Autowired
     private var authenticationManager: AppAuthenticationManager? = null

    @Autowired
    private val securityContextRepository: SecurityContextRepository? = null

    @Bean
    fun springWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain? {
        val patterns = arrayOf("/auth/**")
        return http.cors().disable()
            .exceptionHandling()
            .authenticationEntryPoint { swe: ServerWebExchange, e: AuthenticationException? ->
                Mono.fromRunnable { swe.response.statusCode = HttpStatus.UNAUTHORIZED }
            }.accessDeniedHandler { swe: ServerWebExchange, e: AccessDeniedException? ->
                Mono.fromRunnable { swe.response.statusCode = HttpStatus.FORBIDDEN }
            }.and()
            .csrf().disable()
            .authenticationManager(authenticationManager)
            .securityContextRepository(securityContextRepository)
            .authorizeExchange()
            .pathMatchers(*patterns).permitAll()
            .pathMatchers(HttpMethod.OPTIONS).permitAll()
            .anyExchange().authenticated()
            .and()
            .build()
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder? {
        return BCryptPasswordEncoder()
    }
}
