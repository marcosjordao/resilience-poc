package com.marcosjordao.resiliencepoc.webservice.location

import com.marcosjordao.resiliencepoc.api.location.request.LocationCityRequest
import com.marcosjordao.resiliencepoc.api.location.response.LocationCityResponse
import com.marcosjordao.resiliencepoc.api.location.response.LocationStateResponse
import com.marcosjordao.resiliencepoc.business.ibge.gateway.LocationGateway
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class LocationService(
    private val gateway: LocationGateway
) {
    private companion object {
        private val log = KotlinLogging.logger { LocationService::class.java }
    }

    suspend fun getStates(): List<LocationStateResponse> {
        log.info { "Getting states" }
        return gateway.getStates()
    }

    suspend fun getCities(request: LocationCityRequest): List<LocationCityResponse> {
        log.info { "Getting cities from state [id=${request.stateId}]" }
        return gateway.getCities(request)
    }
}