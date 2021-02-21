package com.marcosjordao.resiliencepoc.business.location.gateway

import com.marcosjordao.resiliencepoc.business.location.api.request.LocationCityRequest
import com.marcosjordao.resiliencepoc.business.location.api.response.LocationCityResponse
import com.marcosjordao.resiliencepoc.business.location.api.response.LocationStateResponse

interface LocationGateway {
    suspend fun getStates(): List<LocationStateResponse>
    suspend fun getCities(request: LocationCityRequest): List<LocationCityResponse>
}
