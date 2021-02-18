package com.marcosjordao.resiliencepoc.webservice.location

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class LocationRouter {

    @Bean
    fun stateRoute(handler: LocationHandler) = coRouter {
        accept(MediaType.APPLICATION_JSON).nest {
            "/api/v1/location/states".nest {
                GET("", handler::getStates)
            }
        }
    }
}