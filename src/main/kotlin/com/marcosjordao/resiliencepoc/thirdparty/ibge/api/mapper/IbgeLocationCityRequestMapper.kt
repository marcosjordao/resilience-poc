package com.marcosjordao.resiliencepoc.thirdparty.ibge.api.mapper

import com.marcosjordao.resiliencepoc.api.location.request.LocationCityRequest
import com.marcosjordao.resiliencepoc.thirdparty.ibge.api.request.IbgeLocationCityRequest
import org.springframework.stereotype.Component

@Component
class IbgeLocationCityRequestMapper {

    fun fromLocationCityRequest(locationCityRequest: LocationCityRequest) =
        IbgeLocationCityRequest(
            stateId = locationCityRequest.stateId
        )

}