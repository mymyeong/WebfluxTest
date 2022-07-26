package com.mymyeong.webfluxtest.user

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromValue
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.*
import reactor.core.publisher.Mono
import java.net.URI
import java.util.*
import java.util.stream.Collectors.toList

@Component
class UserHandler(private val userRepository: UserRepository) {

    fun findAllUsers(req: ServerRequest): Mono<ServerResponse> =
        userRepository.findAll().filter(Objects::nonNull)
            .collect(toList())
            .flatMap { ok().body(fromValue(it)) }

    fun findUser(req: ServerRequest): Mono<ServerResponse> =
        userRepository.findById(req.pathVariable("id").toLong())
            .flatMap { ok().body(fromValue(it)) }
            .switchIfEmpty(status(HttpStatus.NOT_FOUND).build())

    fun saveUser(req: ServerRequest): Mono<ServerResponse> =
        userRepository.saveAll(req.bodyToMono(User::class.java))
            .flatMap { created(URI.create("/users/${it.id}")).build() }
            .next()

    fun updateUser(req: ServerRequest): Mono<ServerResponse> =
        userRepository.findById(req.pathVariable("id").toLong())
            .filter(Objects::nonNull)
            .flatMap {
                val reqUser = req.bodyToMono(User::class.java)
                userRepository.saveAll(reqUser)
                created(URI.create("users/${it.id}")).build()
            }
            .switchIfEmpty(status(HttpStatus.NOT_FOUND).build())

    fun deleteUser(req: ServerRequest): Mono<ServerResponse> =
        userRepository.findById(req.pathVariable("id").toLong())
            .filter(Objects::nonNull)
            .flatMap { user ->
                ok().build(userRepository.deleteById(user.id))
            }
            .switchIfEmpty(status(HttpStatus.NOT_FOUND).build())

}