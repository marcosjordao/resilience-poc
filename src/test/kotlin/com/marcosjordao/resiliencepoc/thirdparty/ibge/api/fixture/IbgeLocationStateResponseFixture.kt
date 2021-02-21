package com.marcosjordao.resiliencepoc.thirdparty.ibge.api.fixture

import com.marcosjordao.resiliencepoc.thirdparty.ibge.api.response.IbgeLocationStateResponse

class IbgeLocationStateResponseFixture {

    companion object {
        fun defaultIbgeLocationStateResponse() =
            IbgeLocationStateResponse(
                id = 1,
                sigla = "ST",
                nome = "State",
                regiao = IbgeLocationStateResponse.IbgeLocationRegion(
                    id = 2,
                    sigla = "RE",
                    nome = "Region"
                )
            )
    }

}
