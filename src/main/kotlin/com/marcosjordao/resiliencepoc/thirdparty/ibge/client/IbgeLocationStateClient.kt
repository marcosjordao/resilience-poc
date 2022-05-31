package com.marcosjordao.resiliencepoc.thirdparty.ibge.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.marcosjordao.resiliencepoc.common.resilience.RetryFactory
import com.marcosjordao.resiliencepoc.thirdparty.ibge.api.response.IbgeLocationStateResponse
import com.marcosjordao.resiliencepoc.thirdparty.ibge.config.IbgeLocationHttpClientConfiguration
import com.marcosjordao.resiliencepoc.thirdparty.ibge.exception.IbgeLocationException
import io.github.resilience4j.kotlin.retry.executeSuspendFunction
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.awaitBody

@Component
class IbgeLocationStateClient(
    config: IbgeLocationHttpClientConfiguration,
    retryFactory: RetryFactory,
    objectMapper: ObjectMapper
) {

    companion object {
        private val log = KotlinLogging.logger { IbgeLocationStateClient::class.java }
        const val API_URI = "/estados"
    }

    private val webClient = config.buildClient(objectMapper)
    private val retry = retryFactory.buildRetry("ibgeStateClient")

    suspend fun getStates(): List<IbgeLocationStateResponse> {
        log.info { "Getting states on IBGE API" }

        val client = webClient.get()
            .uri(API_URI)

        return try {

            retry.executeSuspendFunction {
                client.retrieve().awaitBody()
            }

        } catch (e: Exception) {
            log.error(e) { "Unexpected error trying to get states" }
            throw IbgeLocationException("Unexpected error", e)
        }
    }
}
