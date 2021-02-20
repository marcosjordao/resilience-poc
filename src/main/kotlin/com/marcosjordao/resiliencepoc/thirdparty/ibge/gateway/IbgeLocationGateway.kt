package com.marcosjordao.resiliencepoc.thirdparty.ibge.gateway

import com.marcosjordao.resiliencepoc.api.location.response.LocationStateResponse
import com.marcosjordao.resiliencepoc.business.ibge.gateway.LocationGateway
import com.marcosjordao.resiliencepoc.thirdparty.ibge.api.mapper.IbgeLocationStateResponseMapper
import com.marcosjordao.resiliencepoc.thirdparty.ibge.client.IbgeLocationStateClient
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class IbgeLocationGateway(
    private val stateClient: IbgeLocationStateClient,
    private val mapper: IbgeLocationStateResponseMapper
) : LocationGateway {

    private companion object {
        private val log = KotlinLogging.logger { IbgeLocationGateway::class.java }
    }

    override suspend fun getStates(): List<LocationStateResponse> {
        log.info { "Getting states" }

        val states = stateClient.getStates()
        return states.map(mapper::toLocationStateResponse)
    }
}