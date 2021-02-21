package com.marcosjordao.resiliencepoc.thirdparty.ibge.api.mapper

import com.marcosjordao.resiliencepoc.business.location.api.response.LocationStateResponse
import com.marcosjordao.resiliencepoc.thirdparty.ibge.api.response.IbgeLocationStateResponse
import org.springframework.stereotype.Component

@Component
class IbgeLocationStateResponseMapper {

    fun toLocationStateResponse(ibgeLocationStateResponse: IbgeLocationStateResponse) =
        LocationStateResponse(
            id = ibgeLocationStateResponse.id,
            acronym = ibgeLocationStateResponse.sigla,
            name = ibgeLocationStateResponse.nome
        )
}
