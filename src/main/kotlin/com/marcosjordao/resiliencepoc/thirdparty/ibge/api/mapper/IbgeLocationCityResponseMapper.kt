package com.marcosjordao.resiliencepoc.thirdparty.ibge.api.mapper

import com.marcosjordao.resiliencepoc.api.location.response.LocationCityResponse
import com.marcosjordao.resiliencepoc.thirdparty.ibge.api.response.IbgeLocationCityResponse
import org.springframework.stereotype.Component

@Component
class IbgeLocationCityResponseMapper {

    fun toLocationCityResponse(ibgeLocationCityResponse: IbgeLocationCityResponse) =
        LocationCityResponse(
            id = ibgeLocationCityResponse.id,
            name = ibgeLocationCityResponse.nome
        )

}