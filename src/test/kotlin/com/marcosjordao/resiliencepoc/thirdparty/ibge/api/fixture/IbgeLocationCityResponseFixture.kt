package com.marcosjordao.resiliencepoc.thirdparty.ibge.api.fixture

import com.marcosjordao.resiliencepoc.thirdparty.ibge.api.response.IbgeLocationCityResponse

class IbgeLocationCityResponseFixture {

    companion object {
        fun defaultIbgeLocationCityResponse() =
            IbgeLocationCityResponse(
                id = 1,
                nome = "City"
            )
    }

}
