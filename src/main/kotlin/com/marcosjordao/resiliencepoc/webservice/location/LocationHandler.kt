package com.marcosjordao.resiliencepoc.webservice.location

import com.marcosjordao.resiliencepoc.api.location.request.LocationCityRequest
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*

@Component
class LocationHandler(
    private val service: LocationService
) {

    private companion object {
        private val log = KotlinLogging.logger { LocationHandler::class.java }
    }

    suspend fun getStates(request: ServerRequest): ServerResponse {
        log.info { "Getting states" }

        return try {
            val states = service.getStates()
            ServerResponse.ok().bodyValueAndAwait(states)
        } catch (ex: Exception) {
            ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).buildAndAwait()
        }
    }

    suspend fun getCities(request: ServerRequest): ServerResponse {
        log.info { "Getting cities" }

        return try {
            val cityRequest = LocationCityRequest(request.pathVariable("id"))

            val cities = service.getCities(cityRequest)
            ServerResponse.ok().bodyValueAndAwait(cities)
        } catch (ex: Exception) {
            ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).buildAndAwait()
        }
    }

}