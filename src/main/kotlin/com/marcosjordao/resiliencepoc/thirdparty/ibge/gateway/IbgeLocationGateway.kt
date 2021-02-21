package com.marcosjordao.resiliencepoc.thirdparty.ibge.gateway

import com.marcosjordao.resiliencepoc.business.location.api.request.LocationCityRequest
import com.marcosjordao.resiliencepoc.business.location.api.response.LocationCityResponse
import com.marcosjordao.resiliencepoc.business.location.api.response.LocationStateResponse
import com.marcosjordao.resiliencepoc.business.location.gateway.LocationGateway
import com.marcosjordao.resiliencepoc.thirdparty.ibge.api.mapper.IbgeLocationCityRequestMapper
import com.marcosjordao.resiliencepoc.thirdparty.ibge.api.mapper.IbgeLocationCityResponseMapper
import com.marcosjordao.resiliencepoc.thirdparty.ibge.api.mapper.IbgeLocationStateResponseMapper
import com.marcosjordao.resiliencepoc.thirdparty.ibge.client.IbgeLocationCityClient
import com.marcosjordao.resiliencepoc.thirdparty.ibge.client.IbgeLocationStateClient
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class IbgeLocationGateway(
    private val stateClient: IbgeLocationStateClient,
    private val cityClient: IbgeLocationCityClient,
    private val stateResponseMapper: IbgeLocationStateResponseMapper,
    private val cityResponseMapper: IbgeLocationCityResponseMapper,
    private val cityRequestMapper: IbgeLocationCityRequestMapper
) : LocationGateway {

    private companion object {
        private val log = KotlinLogging.logger { IbgeLocationGateway::class.java }
    }

    override suspend fun getStates(): List<LocationStateResponse> {
        log.info { "Getting states" }

        val states = stateClient.getStates()
        return states.map(stateResponseMapper::toLocationStateResponse)
    }

    override suspend fun getCities(request: LocationCityRequest): List<LocationCityResponse> {
        log.info { "Getting cities from state [id=${request.stateId}]" }

        val ibgeCityRequest = cityRequestMapper.fromLocationCityRequest(request)
        val cities = cityClient.getCities(ibgeCityRequest)

        return cities.map(cityResponseMapper::toLocationCityResponse)
    }
}
