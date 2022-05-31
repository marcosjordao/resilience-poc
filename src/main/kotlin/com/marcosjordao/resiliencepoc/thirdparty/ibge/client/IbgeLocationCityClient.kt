package com.marcosjordao.resiliencepoc.thirdparty.ibge.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.marcosjordao.resiliencepoc.common.resilience.CircuitBreakerFactory
import com.marcosjordao.resiliencepoc.thirdparty.ibge.api.request.IbgeLocationCityRequest
import com.marcosjordao.resiliencepoc.thirdparty.ibge.api.response.IbgeLocationCityResponse
import com.marcosjordao.resiliencepoc.thirdparty.ibge.config.IbgeLocationHttpClientConfiguration
import com.marcosjordao.resiliencepoc.thirdparty.ibge.exception.IbgeLocationException
import io.github.resilience4j.kotlin.circuitbreaker.executeSuspendFunction
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.awaitBody

@Component
class IbgeLocationCityClient(
    config: IbgeLocationHttpClientConfiguration,
    circuitBreakerFactory: CircuitBreakerFactory,
    objectMapper: ObjectMapper
) {
    companion object {
        private val log = KotlinLogging.logger { IbgeLocationStateClient::class.java }
    }

    private val webClient = config.buildClient(objectMapper)
    private val circuitBreaker = circuitBreakerFactory.buildCircuitBreaker("ibgeCityClient")

    suspend fun getCities(request: IbgeLocationCityRequest): List<IbgeLocationCityResponse> {
        log.info { "Getting cities on IBGE API from state [id=${request.stateId}]" }

        val uri = "/estados/${request.stateId}/municipios"

        val client = webClient.get()
            .uri(uri)

        return try {
            circuitBreaker.executeSuspendFunction {
                client.retrieve().awaitBody()
            }
        } catch (e: Exception) {
            log.error(e) { "Unexpected error trying to get cities" }
            throw IbgeLocationException("Unexpected error", e)
        }
    }
}
