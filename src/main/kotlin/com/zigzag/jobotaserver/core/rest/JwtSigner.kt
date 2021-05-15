package com.zigzag.jobotaserver.core.rest

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.security.KeyPair
import java.time.Duration
import java.time.Instant
import java.util.*

@Service
class JwtSigner {
    val keyPair: KeyPair = Keys.keyPairFor(SignatureAlgorithm.RS256)

    fun createJwt(userId: String,roles:List<String>): String {
        return Jwts.builder()
            .signWith(keyPair.private, SignatureAlgorithm.RS256)
            .setSubject(userId)
            .setIssuer("identity")
            .setExpiration(Date.from(Instant.now().plus(Duration.ofMinutes(15))))
            .setIssuedAt(Date.from(Instant.now()))
            .claim("roles",roles)
            .compact()
    }

    fun validateJwt(jwt: String): Jws<Claims> {
        return Jwts.parserBuilder()
            .setSigningKey(keyPair.public)
            .build()
            .parseClaimsJws(jwt)
    }

    fun getEmailFromToken(token: String?): String? {
        return getClaimValueFromToken(token, { obj: Claims -> obj.subject }) as String
    }

    fun getExpirationDateFromToken(token: String?): Date {
        return getClaimValueFromToken(token, { obj: Claims -> obj.expiration }) as Date
    }

    fun getClaimValueFromToken(token: String?, claimsResolver: (Claims)->Any): Any {
        val claims = getAllClaimsFromToken(token)
        return claimsResolver(claims)
    }

    fun getAllClaimsFromToken(token: String?): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(keyPair.public)
             .build()
            .parseClaimsJws(token)
            .getBody()
    }

    fun isTokenExpired(token: String): Boolean {
        val expiration = getExpirationDateFromToken(token)
        return expiration.before(Date())
    }

}