package com.mymyeong.webfluxtest.weather

import io.netty.channel.ChannelOption
import io.netty.handler.ssl.SslContextBuilder
import io.netty.handler.ssl.util.InsecureTrustManagerFactory
import io.netty.handler.timeout.ReadTimeoutException
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.netty.http.client.HttpClient
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.net.ssl.SSLException

@Component
class WeatherService(
    private val weatherConfig: WeatherConfig
) {

    fun getMidTaBaseUri(pageNo: Int, numOfRows: Int, stnId: String, tmFc: String): Mono<String> {
        val httpClient: HttpClient = getHttpClient()

        return WebClient.builder()
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .baseUrl(weatherConfig.url)
            .build()
            .get()
            .uri(
                "?serviceKey=${weatherConfig.serviceKey}" +
                        "&stnId=$stnId" +
                        "&pageNo=$pageNo" +
                        "&numOfRows=$numOfRows" +
                        "&tmFc=$tmFc" +
                        "&dataType=JSON"
            )
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(HttpStatus::is5xxServerError) {
                Mono.error(ReadTimeoutException("500 Error"))
            }
            .onStatus(HttpStatus::is4xxClientError) {
                Mono.error(ReadTimeoutException("400 Error"))
            }
            .bodyToMono(WeatherMidFcst::class.java)
            .timeout(Duration.ofSeconds(5), Mono.error(ReadTimeoutException("Timeout")))
            .map {
                it.response.body.items.item[0].wfSv
            }

    }

    private fun getHttpClient() = HttpClient
        .create()
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
        .doOnConnected { connection ->
            connection.addHandlerLast(ReadTimeoutHandler(5))
                .addHandlerLast(WriteTimeoutHandler(5))
        }
        .responseTimeout(Duration.ofSeconds(5))
        .secure { t ->
            try {
                t.sslContext(SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build())
            } catch (e: SSLException) {
                println("SSLException $e")
            }
        }

    fun getCurrentTmFc(): String {
        val timeFormatter = DateTimeFormatter.ofPattern("HHmm")
        val tm06 = LocalTime.parse("0600", timeFormatter)
        val tm18 = LocalTime.parse("1800", timeFormatter)
        var baseDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        val baseTimeTm =
            if (LocalTime.now() > tm18) {
                "1800"
            } else if (LocalTime.now() > tm06) {
                "0600"
            } else {
                baseDate = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                "1800"
            }

        return baseDate + baseTimeTm
    }
}