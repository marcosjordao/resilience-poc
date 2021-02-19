package com.marcosjordao.resiliencepoc.thirdparty.ibge.client

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.marcosjordao.resiliencepoc.common.resilience.RetryFactory
import com.marcosjordao.resiliencepoc.thirdparty.ibge.api.response.IbgeLocationStateResponse
import com.marcosjordao.resiliencepoc.thirdparty.ibge.config.IbgeLocationHttpClientConfiguration
import com.marcosjordao.resiliencepoc.thirdparty.ibge.exception.IbgeLocationException
import io.github.resilience4j.kotlin.retry.executeSuspendFunction
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.awaitBody

@Component
class IbgeLocationClient(
    config: IbgeLocationHttpClientConfiguration,
    retryFactory: RetryFactory
) {
    companion object {
        private val log = KotlinLogging.logger { IbgeLocationClient::class.java }
        const val STATES_URI = "/estados"
    }

    private val webClient = config.buildClient(jacksonObjectMapper())
    private val retry = retryFactory.buildRetry("ibgeLocationClientRetry")

    suspend fun getStates(): List<IbgeLocationStateResponse> {
        log.info { "Getting states on IBGE API" }

        val client = webClient.get()
            .uri(STATES_URI)

        return try {

            retry.executeSuspendFunction {
                client.retrieve()
            }.awaitBody()

        } catch (e: Exception) {
            log.error(e) { "Unexpected error trying to get states" }
            throw IbgeLocationException("Unexpected error", e)
        }
    }
}