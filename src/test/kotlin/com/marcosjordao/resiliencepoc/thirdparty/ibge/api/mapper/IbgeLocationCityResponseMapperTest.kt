package com.marcosjordao.resiliencepoc.thirdparty.ibge.api.mapper

import com.marcosjordao.resiliencepoc.thirdparty.ibge.api.fixture.IbgeLocationCityResponseFixture
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class IbgeLocationCityResponseMapperTest {

    private val mapper = IbgeLocationCityResponseMapper()

    @Test
    fun `should map IbgeLocationCityResponse to LocationCityResponse successfully`() = runBlockingTest {
        val ibgeLocationCityResponse = IbgeLocationCityResponseFixture.defaultIbgeLocationCityResponse()

        val mappedLocationCityResponse = mapper.toLocationCityResponse(ibgeLocationCityResponse)

        assertAll(
            { assertNotNull(mappedLocationCityResponse) },
            { assertEquals(ibgeLocationCityResponse.id, mappedLocationCityResponse.id) },
            { assertEquals(ibgeLocationCityResponse.nome, mappedLocationCityResponse.name) }
        )
    }
}
