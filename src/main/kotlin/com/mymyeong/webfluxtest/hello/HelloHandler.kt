package com.mymyeong.webfluxtest.hello

import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromValue
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono

@Component
class HelloHandler {

    fun hello(req: ServerRequest): Mono<ServerResponse> {
        return ok().contentType(MediaType.TEXT_PLAIN)
            .body(fromValue("hello ${req.pathVariable("name")}"))
    }
}