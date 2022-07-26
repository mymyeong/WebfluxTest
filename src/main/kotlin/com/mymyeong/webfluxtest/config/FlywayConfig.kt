package com.mymyeong.webfluxtest.config

import org.flywaydb.core.Flyway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

@Configuration
class FlywayConfig(private val env: Environment) {
    @Bean(initMethod = "migrate")
    fun flyway(): Flyway? {
        return Flyway(Flyway.configure()
            .baselineOnMigrate(true)
            .dataSource(
                env.getProperty("spring.flyway.url"),
                env.getProperty("spring.r2dbc.username"),
                env.getProperty("spring.r2dbc.password")
            )
        )
    }
}