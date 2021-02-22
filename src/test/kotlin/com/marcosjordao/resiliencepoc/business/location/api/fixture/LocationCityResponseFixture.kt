package com.marcosjordao.resiliencepoc.business.location.api.fixture

import com.marcosjordao.resiliencepoc.business.location.api.response.LocationCityResponse

class LocationCityResponseFixture {

    companion object {
        fun defaultLocationCityResponse() =
            LocationCityResponse(
                id = 1,
                name = "City"
            )
    }

}
