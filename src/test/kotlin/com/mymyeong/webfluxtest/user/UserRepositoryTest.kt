package com.mymyeong.webfluxtest.user

import io.kotest.core.spec.style.FunSpec
import io.mockk.mockk
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [UserRepository::class])
class UserRepositoryTest(
)    : FunSpec({

    val userRepository = mockk<UserRepository>(relaxed = true)
    val target = UserHandler(userRepository)

    test("유저 저장") {
        val user = mockk<User>()
        val savedUser = userRepository.save(user)

    }

})