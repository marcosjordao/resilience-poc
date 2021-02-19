package com.marcosjordao.resiliencepoc.thirdparty.ibge.gateway

import com.marcosjordao.resiliencepoc.api.location.response.LocationStateResponse
import com.marcosjordao.resiliencepoc.business.ibge.gateway.LocationGateway
import com.marcosjordao.resiliencepoc.thirdparty.ibge.api.mapper.IbgeLocationStateResponseMapper
import com.marcosjordao.resiliencepoc.thirdparty.ibge.client.IbgeLocationClient
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.kotlin.circuitbreaker.executeSuspendFunction
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class IbgeLocationGateway(
    private val client: IbgeLocationClient,
    private val mapper: IbgeLocationStateResponseMapper
) : LocationGateway {

    private companion object {
        private val log = KotlinLogging.logger { IbgeLocationGateway::class.java }
    }

    override suspend fun getStates(): List<LocationStateResponse> {
        log.info { "Getting states" }

//        val circuitBreaker = CircuitBreaker.ofDefaults("ibgeLocationState")
//        val states = circuitBreaker.executeSuspendFunction {
//            client.getStates()
//        }

        val states = client.getStates()
        return states.map(mapper::toLocationStateResponse)
    }
}