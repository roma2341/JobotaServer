package com.zigzag.jobotaserver.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@Profile(value=["dev","test"])
@Configuration
@EnableWebFluxSecurity
class DevSecurityConfig {
     @Bean
     fun securityWebFilterChain(http:ServerHttpSecurity): SecurityWebFilterChain {
        return http
                 .csrf().disable()
                .authorizeExchange()
                .anyExchange()
                .permitAll()
                .and()
                .build()
     }
}
