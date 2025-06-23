package com.example.authservice.contoller

import com.example.authservice.dto.MemberDto
import com.example.authservice.service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/signup")
    fun signup(@RequestBody request: MemberDto.SignupRequest): ResponseEntity<String> {
        authService.signup(request)
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공")
    }

    @PostMapping("/login")
    fun login(@RequestBody request: MemberDto.LoginRequest): ResponseEntity<MemberDto.TokenResponse> {
        val tokens = authService.login(request)
        return ResponseEntity.ok(MemberDto.TokenResponse(tokens.accessToken, tokens.refreshToken))
    }
}