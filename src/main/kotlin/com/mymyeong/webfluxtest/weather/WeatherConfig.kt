package com.mymyeong.webfluxtest.weather

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "weather")
class WeatherConfig(
    val url: String,
    val serviceKey: String
)