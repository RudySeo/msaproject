package com.example.authservice.dto

class MemberDto {

    data class SignupRequest(val email: String, val password: String)

    data class LoginRequest(val email: String, val password: String)

    data class TokenResponse(val accessToken: String, val refreshToken: String)
}