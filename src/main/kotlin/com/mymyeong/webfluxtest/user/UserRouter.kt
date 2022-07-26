package com.mymyeong.webfluxtest.user

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RequestPredicates.path
import org.springframework.web.reactive.function.server.RouterFunctions.nest
import org.springframework.web.reactive.function.server.router

@Configuration
class UserRouter(
    private val userHandler: UserHandler
) {
    @Bean
    fun userRoute() = nest(path("/users"),
        router {
            listOf(
                GET("", userHandler::findAllUsers),
                GET("/{id}", userHandler::findUser),
                POST("/{id}", userHandler::saveUser),
                PUT("/{id}", userHandler::updateUser),
                DELETE("/{id}", userHandler::deleteUser)
            )
        })
}