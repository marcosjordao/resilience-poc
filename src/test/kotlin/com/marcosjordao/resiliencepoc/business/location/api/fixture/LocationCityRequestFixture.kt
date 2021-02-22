package com.marcosjordao.resiliencepoc.business.location.api.fixture

import com.marcosjordao.resiliencepoc.business.location.api.request.LocationCityRequest

class LocationCityRequestFixture {

    companion object {
        fun defaultLocationCityRequest() =
            LocationCityRequest(
                stateId = "33"
            )
    }

}
