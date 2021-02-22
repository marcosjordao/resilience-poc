package com.marcosjordao.resiliencepoc.business.location.api.fixture

import com.marcosjordao.resiliencepoc.business.location.api.response.LocationStateResponse

class LocationStateResponseFixture {

    companion object {
        fun defaultLocationStateResponse() =
            LocationStateResponse(
                id = 1,
                acronym = "ST",
                name = "State"
            )
    }

}
