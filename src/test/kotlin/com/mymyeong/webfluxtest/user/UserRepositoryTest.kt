package com.mymyeong.webfluxtest.user

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [UserRepository::class])
class UserRepositoryTest(
)    : FunSpec({

    val userRepository = mockk<UserRepository>(relaxed = true)

    test("유저 저장") {
        every { userRepository.save(any()) } returns mockk()
    }

    test("유저 업데이트") {
        val user = User(
            name = "test1",
            passwd = "test"
        )
        every { userRepository.save(user).blockOptional().get() } answers { user }

        val savedUser = withContext(Dispatchers.IO) {
            userRepository.save(user).blockOptional()
        }.get()

        savedUser.name shouldBe user.name
        savedUser.passwd shouldBe user.passwd
    }

    test("유저 조회") {
        val user = User(
            name = "test1",
            passwd = "test"
        )
        every { userRepository.findById(1L).blockOptional().get() } answers { user }

        val findUser = withContext(Dispatchers.IO) {
            userRepository.findById(1L).blockOptional()
        }.get()
        findUser.name shouldBe user.name
    }
})