package com.example.gatewayservice.config

import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RouteConfig {

    @Bean
    fun routeLocator(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes()

            .route("auth-service") {
                it.path("/auth/**")
                    .uri("http://localhost:8082") // 로그인, 회원가입 등
            }
            .build()
    }
}