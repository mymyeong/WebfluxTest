package com.mymyeong.webfluxtest.user

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(name = "my_user")
data class User(
    @Id
    var id: Long = 0,
    var name: String = "",
    var passwd: String = ""
)