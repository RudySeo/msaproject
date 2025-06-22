package com.example.gatewayservice.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component

@Component
class JwtProvider {

    private val secret = "your-very-secure-key-should-be-long".toByteArray()
    private val key = Keys.hmacShaKeyFor(secret)

    fun getClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
    }
}