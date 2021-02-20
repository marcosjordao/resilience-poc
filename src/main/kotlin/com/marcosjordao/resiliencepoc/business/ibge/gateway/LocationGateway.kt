package com.marcosjordao.resiliencepoc.business.ibge.gateway

import com.marcosjordao.resiliencepoc.api.location.request.LocationCityRequest
import com.marcosjordao.resiliencepoc.api.location.response.LocationCityResponse
import com.marcosjordao.resiliencepoc.api.location.response.LocationStateResponse

interface LocationGateway {
    suspend fun getStates(): List<LocationStateResponse>
    suspend fun getCities(request: LocationCityRequest): List<LocationCityResponse>
}