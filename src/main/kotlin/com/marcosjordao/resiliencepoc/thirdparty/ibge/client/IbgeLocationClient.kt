package com.marcosjordao.resiliencepoc.thirdparty.ibge.client

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.marcosjordao.resiliencepoc.thirdparty.ibge.api.response.IbgeLocationStateResponse
import com.marcosjordao.resiliencepoc.thirdparty.ibge.config.IbgeLocationHttpClientConfiguration
import com.marcosjordao.resiliencepoc.thirdparty.ibge.exception.IbgeLocationException
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.awaitBody

@Component
class IbgeLocationClient(
    config: IbgeLocationHttpClientConfiguration
) {
    companion object {
        private val log = KotlinLogging.logger { IbgeLocationClient::class.java }
        const val STATES_URI = "/estados"
    }

    private val webClient = config.buildClient(jacksonObjectMapper())

    suspend fun getStates(): List<IbgeLocationStateResponse> {
        log.info { "Getting states on IBGE API" }

        val client = webClient.get()
                              .uri(STATES_URI)

        return try {
            client.retrieve().awaitBody()
        } catch (e: Exception) {
            log.error(e) { "Unexpected error trying to get states" }
            throw IbgeLocationException("Unexpected error", e)
        }
    }
}