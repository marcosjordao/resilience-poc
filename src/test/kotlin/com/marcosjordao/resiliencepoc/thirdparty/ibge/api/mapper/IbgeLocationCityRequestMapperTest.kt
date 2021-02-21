package com.marcosjordao.resiliencepoc.thirdparty.ibge.api.mapper

import com.marcosjordao.resiliencepoc.business.location.api.request.LocationCityRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class IbgeLocationCityRequestMapperTest {

    private val mapper = IbgeLocationCityRequestMapper()

    @Test
    fun `should map LocationCityRequest to IbgeLocationCityRequest successfully`() = runBlockingTest {
        val locationCityRequest = LocationCityRequest(stateId = "33")

        val mappedIbgeLocationCityRequest = mapper.fromLocationCityRequest(locationCityRequest)

        assertAll(
            { assertNotNull(mappedIbgeLocationCityRequest) },
            { assertEquals(locationCityRequest.stateId, mappedIbgeLocationCityRequest.stateId) }
        )
    }
}
