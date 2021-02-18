package com.marcosjordao.resiliencepoc.webservice.location

import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait

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

}