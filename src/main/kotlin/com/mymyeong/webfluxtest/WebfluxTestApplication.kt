package com.mymyeong.webfluxtest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class WebfluxTestApplication

fun main(args: Array<String>) {
    runApplication<WebfluxTestApplication>(*args)
}
