package com.marcosjordao.resiliencepoc.business.ibge.gateway

import com.marcosjordao.resiliencepoc.api.location.response.LocationStateResponse

interface LocationGateway {
    suspend fun getStates(): List<LocationStateResponse>
}