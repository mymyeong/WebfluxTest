package com.mymyeong.webfluxtest.weather

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RequestPredicates.path
import org.springframework.web.reactive.function.server.RouterFunctions.nest
import org.springframework.web.reactive.function.server.router

@Configuration
class WeatherRouter(
    private val weatherHandler: WeatherHandler
) {

    @Bean
    fun weatherRoute() = nest(path("/weather"),
        router {
            listOf(
                GET("", weatherHandler::getWeather)
            )
        })
}