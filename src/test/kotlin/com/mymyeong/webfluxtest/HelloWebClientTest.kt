package com.mymyeong.webfluxtest

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@ExtendWith(SpringExtension::class, MockitoExtension::class)
@Import(HelloHandler::class)
@WebFluxTest
class HelloWebClientTest {
    @MockBean
    lateinit var webClient: WebTestClient

    @BeforeEach
    fun setUp() {
        val routerFunction = HelloRouter(HelloHandler()).helloRoute()
        webClient = WebTestClient.bindToRouterFunction(routerFunction).build()
    }

    @Test
    fun testHello() {
        val name = "mymyeong"

        webClient
            .get().uri("/hello/{name}", name)
            .exchange()
            .expectStatus().isOk
            .expectBody<String>()
            .isEqualTo("hello $name")
    }
}
