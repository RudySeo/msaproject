package com.example.authservice.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class JwtConfig {

    @Value("\${jwt.secret}")
    var secret: String = ""

    @Value("\${jwt.access-exp}")
    var accessTokenValidity: Long = 0

    @Value("\${jwt.refresh-exp}")
    var refreshTokenValidity: Long = 0
}
