package com.marcosjordao.resiliencepoc.thirdparty.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.http.codec.ClientCodecConfigurer
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.util.concurrent.TimeUnit


abstract class HttpClientConfiguration(
    private val url: String,
    private val connectTimeout: Int,
    private val connectionWriteTimeout: Long,
    private val connectionReadTimeout: Long,
) {

    fun buildClient(objectMapper: ObjectMapper): WebClient {
        val httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout)
            .doOnConnected { connection ->
                connection.addHandlerLast(ReadTimeoutHandler(connectionReadTimeout, TimeUnit.MILLISECONDS))
                          .addHandlerLast(WriteTimeoutHandler(connectionWriteTimeout, TimeUnit.MILLISECONDS))
            }

        return WebClient.builder()
            .baseUrl(url)
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .exchangeStrategies(buildExchangeStrategies(objectMapper))
            .build()
    }

    private fun buildExchangeStrategies(objectMapper: ObjectMapper): ExchangeStrategies {
        return ExchangeStrategies.builder()
            .codecs {
                clientDefaultCodecsConfigurer: ClientCodecConfigurer ->
                    clientDefaultCodecsConfigurer.defaultCodecs()
                                                 .jackson2JsonEncoder(Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON))

                    clientDefaultCodecsConfigurer.defaultCodecs()
                                                 .jackson2JsonDecoder(Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON))
            }
            .build()
    }
}