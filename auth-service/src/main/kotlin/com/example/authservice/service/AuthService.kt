package com.example.authservice.service

import com.example.authservice.dto.MemberDto
import com.example.authservice.entity.Member
import com.example.authservice.entity.RefreshToken
import com.example.authservice.entity.Role
import com.example.authservice.repository.MemberRepository
import com.example.authservice.repository.RefreshTokenRepository
import com.example.authservice.security.JwtProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val memberRepository: MemberRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtProvider: JwtProvider
) {
    @Transactional
    fun signup(request: MemberDto.SignupRequest) {
        if (memberRepository.existsByEmail(request.email)) {
            throw IllegalStateException("이미 존재하는 이메일입니다.")
        }


        val member = Member(email = request.email, password = request.password, role = Role.USER)
        memberRepository.save(member)
    }

    @Transactional
    fun login(request: MemberDto.LoginRequest): MemberDto.TokenResponse {
        val member = memberRepository.findByEmail(request.email)
            ?: throw IllegalArgumentException("해당 이메일 사용자가 없습니다.")

        // 비밀번호 검증(수정 필요)
        if (member.password != request.password)
            throw IllegalArgumentException("비밀번호가 일치하지 않습니다.")

        val accessToken = jwtProvider.createAccessToken(member)
        val refreshToken = jwtProvider.createRefreshToken(member)

        val existingToken = refreshTokenRepository.findByMemberId(member.id)

        if (existingToken != null) {
            existingToken.token = refreshToken
            refreshTokenRepository.save(existingToken)
        } else {
            refreshTokenRepository.save(
                RefreshToken(
                    token = refreshToken,
                    member = member
                )
            )
        }

        return MemberDto.TokenResponse(accessToken, refreshToken)
    }
}
