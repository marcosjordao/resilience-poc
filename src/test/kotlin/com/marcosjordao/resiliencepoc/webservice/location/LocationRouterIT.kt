package com.marcosjordao.resiliencepoc.webservice.location

import com.marcosjordao.resiliencepoc.business.location.api.fixture.LocationCityRequestFixture
import com.marcosjordao.resiliencepoc.business.location.api.fixture.LocationCityResponseFixture
import com.marcosjordao.resiliencepoc.business.location.api.fixture.LocationStateResponseFixture
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
@ExperimentalCoroutinesApi
@TestInstance(Lifecycle.PER_CLASS)
internal class LocationRouterIT {

    private lateinit var webTestClient: WebTestClient

    @Autowired
    private lateinit var locationHandler: LocationHandler

    @MockBean
    private lateinit var locationService: LocationService

    @BeforeAll
    fun setUp() {
        webTestClient = WebTestClient
            .bindToRouterFunction(LocationRouter().locationRoute(locationHandler))
            .build()
    }

    @Test
    fun `should getStates`() = runBlockingTest {
        `when`(locationService.getStates()).thenReturn(listOf(LocationStateResponseFixture.defaultLocationStateResponse()))

        val responseBody = webTestClient.get()
            .uri("/api/v1/location/states")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(List::class.java)
            .returnResult()
            .responseBody

        assertAll(
            { assertNotNull(responseBody) },
            { assertEquals(1, responseBody?.size) }
        )

    }

    @Test
    fun `should getCities`() = runBlockingTest {
        val request = LocationCityRequestFixture.defaultLocationCityRequest()

        `when`(locationService.getCities(request))
            .thenReturn(listOf(LocationCityResponseFixture.defaultLocationCityResponse()))

        val responseBody = webTestClient.get()
            .uri("/api/v1/location/states/33/cities")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(List::class.java)
            .returnResult()
            .responseBody

        assertAll(
            { assertNotNull(responseBody) },
            { assertEquals(1, responseBody?.size) }
        )

    }

}
