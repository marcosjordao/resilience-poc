package com.marcosjordao.resiliencepoc.thirdparty.ibge.api.fixture

import com.marcosjordao.resiliencepoc.thirdparty.ibge.api.request.IbgeLocationCityRequest

class IbgeLocationCityRequestFixture {

    companion object {
        fun defaultIbgeLocationCityRequest() =
            IbgeLocationCityRequest(
                stateId = "33"
            )
    }

}
