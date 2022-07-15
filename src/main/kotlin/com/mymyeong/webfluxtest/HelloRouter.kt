package com.mymyeong.webfluxtest

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.MediaType.TEXT_HTML
import org.springframework.web.reactive.function.server.*

@Configuration
class HelloRouter(
    val helloHandler: HelloHandler
) {
    @Bean
    fun helloRoute() = router {
        accept(TEXT_HTML).nest {
            (GET("/hello/") or GET("/hello/{name}")).invoke(helloHandler::hello)
        }
    }
}