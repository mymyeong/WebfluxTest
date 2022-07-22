package com.mymyeong.webfluxtest

import javax.persistence.*

@Entity
@Table(name = "my_user")
class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    var name: String = ""
    var passwd: String = ""
}