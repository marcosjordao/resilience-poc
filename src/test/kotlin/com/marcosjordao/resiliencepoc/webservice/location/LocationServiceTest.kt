package com.marcosjordao.resiliencepoc.webservice.location

import com.marcosjordao.resiliencepoc.business.location.api.fixture.LocationCityRequestFixture
import com.marcosjordao.resiliencepoc.business.location.gateway.LocationGateway
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
internal class LocationServiceTest {

    @MockK
    private lateinit var locationGateway: LocationGateway

    @InjectMockKs
    private lateinit var locationService: LocationService

    @Test
    fun `should getStates`() = runBlockingTest {
        coEvery { locationGateway.getStates() } returns emptyList()

        val states = locationService.getStates()

        assertAll(
            { assertNotNull(states) },
            { assertTrue(states.isEmpty()) }
        )

        coVerify(exactly = 1) { locationGateway.getStates() }
    }

    @Test
    fun `should getCities`() = runBlockingTest {
        coEvery { locationGateway.getCities(any()) } returns emptyList()

        val request = LocationCityRequestFixture.defaultLocationCityRequest()
        val cities = locationService.getCities(request)

        assertAll(
            { assertNotNull(cities) },
            { assertTrue(cities.isEmpty()) }
        )

        coVerify(exactly = 1) { locationGateway.getCities(any()) }
    }
}
