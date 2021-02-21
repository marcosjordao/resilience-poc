package com.marcosjordao.resiliencepoc.thirdparty.ibge.gateway

import com.marcosjordao.resiliencepoc.business.location.api.fixture.LocationCityRequestFixture
import com.marcosjordao.resiliencepoc.business.location.api.fixture.LocationCityResponseFixture
import com.marcosjordao.resiliencepoc.business.location.api.fixture.LocationStateResponseFixture
import com.marcosjordao.resiliencepoc.thirdparty.ibge.api.fixture.IbgeLocationCityRequestFixture
import com.marcosjordao.resiliencepoc.thirdparty.ibge.api.fixture.IbgeLocationCityResponseFixture
import com.marcosjordao.resiliencepoc.thirdparty.ibge.api.fixture.IbgeLocationStateResponseFixture
import com.marcosjordao.resiliencepoc.thirdparty.ibge.api.mapper.IbgeLocationCityRequestMapper
import com.marcosjordao.resiliencepoc.thirdparty.ibge.api.mapper.IbgeLocationCityResponseMapper
import com.marcosjordao.resiliencepoc.thirdparty.ibge.api.mapper.IbgeLocationStateResponseMapper
import com.marcosjordao.resiliencepoc.thirdparty.ibge.client.IbgeLocationCityClient
import com.marcosjordao.resiliencepoc.thirdparty.ibge.client.IbgeLocationStateClient
import com.marcosjordao.resiliencepoc.thirdparty.ibge.exception.IbgeLocationException
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
@ExperimentalCoroutinesApi
internal class IbgeLocationGatewayTest {

    @InjectMockKs
    private lateinit var ibgeLocationGateway: IbgeLocationGateway

    @MockK
    private lateinit var stateClient: IbgeLocationStateClient

    @MockK
    private lateinit var cityClient: IbgeLocationCityClient

    @MockK
    private lateinit var stateResponseMapper: IbgeLocationStateResponseMapper

    @MockK
    private lateinit var cityResponseMapper: IbgeLocationCityResponseMapper

    @MockK
    private lateinit var cityRequestMapper: IbgeLocationCityRequestMapper

    @Test
    fun `should getStates successfully`() = runBlockingTest {
        val ibgeStateResponse = IbgeLocationStateResponseFixture.defaultIbgeLocationStateResponse()
        val stateResponse = LocationStateResponseFixture.defaultLocationStateResponse()

        coEvery { stateClient.getStates() } returns listOf(ibgeStateResponse)
        coEvery { stateResponseMapper.toLocationStateResponse(any()) } returns stateResponse

        val states = ibgeLocationGateway.getStates()

        assertAll(
            { assertNotNull(states) },
            { assertEquals(1, states.size) },
            { assertEquals(stateResponse, states.first()) }
        )
    }

    @Test
    fun `should throw a IbgeLocationException when error occurs in the stateClient`() = runBlockingTest {
        coEvery { stateClient.getStates() } throws IbgeLocationException("exception")

        assertThrows(IbgeLocationException::class.java) {
            runBlocking {
                ibgeLocationGateway.getStates()
            }
        }
    }

    @Test
    fun `should getCities successfully`() = runBlockingTest {
        val ibgeCityResponse = IbgeLocationCityResponseFixture.defaultIbgeLocationCityResponse()
        val cityResponse = LocationCityResponseFixture.defaultLocationCityResponse()

        val ibgeCityRequest = IbgeLocationCityRequestFixture.defaultIbgeLocationCityRequest()
        val cityRequest = LocationCityRequestFixture.defaultLocationCityRequest()

        coEvery { cityClient.getCities(any()) } returns listOf(ibgeCityResponse)
        coEvery { cityResponseMapper.toLocationCityResponse(any()) } returns cityResponse
        coEvery { cityRequestMapper.fromLocationCityRequest(any()) } returns ibgeCityRequest

        val cities = ibgeLocationGateway.getCities(cityRequest)

        assertAll(
            { assertNotNull(cities) },
            { assertEquals(1, cities.size) },
            { assertEquals(cityResponse, cities.first()) }
        )
    }

    @Test
    fun `should throw a IbgeLocationException when error occurs in the cityClient`() = runBlockingTest {
        val ibgeCityRequest = IbgeLocationCityRequestFixture.defaultIbgeLocationCityRequest()
        val cityRequest = LocationCityRequestFixture.defaultLocationCityRequest()

        coEvery { cityRequestMapper.fromLocationCityRequest(any()) } returns ibgeCityRequest
        coEvery { cityClient.getCities(any()) } throws IbgeLocationException("exception")

        assertThrows(IbgeLocationException::class.java) {
            runBlocking {
                ibgeLocationGateway.getCities(cityRequest)
            }
        }
    }

}
