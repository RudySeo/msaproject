package com.example.gatewayservice.util

import com.example.gatewayservice.config.JwtConfig
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component

@Component
class JwtProvider(

    private val config: JwtConfig
) {
    private val key = Keys.hmacShaKeyFor(config.secret.toByteArray())

    fun getClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
    }
}