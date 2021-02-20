package com.marcosjordao.resiliencepoc.thirdparty.ibge.client

import com.marcosjordao.resiliencepoc.common.objectmapper.DefaultObjectMapper
import com.marcosjordao.resiliencepoc.thirdparty.ibge.api.request.IbgeLocationCityRequest
import com.marcosjordao.resiliencepoc.thirdparty.ibge.api.response.IbgeLocationCityResponse
import com.marcosjordao.resiliencepoc.thirdparty.ibge.config.IbgeLocationHttpClientConfiguration
import com.marcosjordao.resiliencepoc.thirdparty.ibge.exception.IbgeLocationException
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.awaitBody

@Component
class IbgeLocationCityClient(
    config: IbgeLocationHttpClientConfiguration
) {
    companion object {
        private val log = KotlinLogging.logger { IbgeLocationStateClient::class.java }
    }

    private val webClient = config.buildClient(DefaultObjectMapper.get())

    suspend fun getCities(request: IbgeLocationCityRequest): List<IbgeLocationCityResponse> {
        log.info { "Getting cities on IBGE API from state [id=${request.stateId}]" }

        val uri = "/estados/${request.stateId}/municipios"

        val client = webClient.get()
            .uri(uri)

        return try {
            client.retrieve().awaitBody()
        } catch (e: Exception) {
            log.error(e) { "Unexpected error trying to get cities" }
            throw IbgeLocationException("Unexpected error", e)
        }
    }
}