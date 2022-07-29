package com.mymyeong.webfluxtest.weather

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono

@Component
class WeatherHandler(private val weatherService: WeatherService) {

    fun getWeather(req: ServerRequest): Mono<ServerResponse> =
        ok().body(weatherService.getMidTaBaseUri(
            pageNo = 1,
            numOfRows = 10,
            stnId = WeatherLocale.SEOUL.stnId,
            tmFc = weatherService.getCurrentTmFc()
        ))
}