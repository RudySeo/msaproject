package com.example.authservice.security

import com.example.authservice.config.JwtConfig
import com.example.authservice.entity.Member
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtProvider(private val config: JwtConfig) {
    private val key = Keys.hmacShaKeyFor(config.secret.toByteArray())

    fun createAccessToken(member: Member): String {
        return Jwts.builder()
            .setSubject(member.id.toString())
            .claim("role", member.role)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + config.accessTokenValidity))
            .signWith(key)
            .compact()
    }

    fun createRefreshToken(member: Member): String {
        return Jwts.builder()
            .setSubject(member.id.toString())
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + config.refreshTokenValidity))
            .signWith(key)
            .compact()
    }

    fun getClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
    }
}