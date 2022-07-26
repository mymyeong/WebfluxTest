package com.mymyeong.webfluxtest.user

import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface UserRepository: ReactiveCrudRepository<User, Long>