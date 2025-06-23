package com.example.gatewayservice.filter

import com.example.gatewayservice.util.JwtProvider
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class JwtGlobalFilter(private val jwtProvider: JwtProvider) : GlobalFilter {

    // 인증 제외할 경로를 리스트로 관리
    private val whiteList = listOf(
        "/auth/login",
        "/auth/signup",
    )

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        val path = exchange.request.uri.path
        println("🔥 GlobalFilter 통과: path = $path")
        // 화이트리스트에 포함된 경로면 인증 필터를 우회
        if (whiteList.any { path.startsWith(it) }) {
            println("✅ 인증 제외 경로: $path")
            return chain.filter(exchange)
        }

        val authHeader = exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION)

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val token = authHeader.removePrefix("Bearer ")

            return try {
                val claims = jwtProvider.getClaims(token)
                val userId = claims.subject
                val role = claims["role"].toString()

                val mutatedRequest = exchange.request.mutate()
                    .header("X-User-Id", userId)
                    .header("X-User-Role", role)
                    .build()

                val mutatedExchange = exchange.mutate().request(mutatedRequest).build()
                chain.filter(mutatedExchange)
            } catch (e: Exception) {
                exchange.response.statusCode = HttpStatus.UNAUTHORIZED
                exchange.response.setComplete()
            }
        }

        // 토큰 없음 또는 형식 오류
        exchange.response.statusCode = HttpStatus.UNAUTHORIZED
        return exchange.response.setComplete()
    }


}