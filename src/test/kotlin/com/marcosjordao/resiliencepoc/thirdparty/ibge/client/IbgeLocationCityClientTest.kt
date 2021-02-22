package com.marcosjordao.resiliencepoc.thirdparty.ibge.client

import com.marcosjordao.resiliencepoc.common.resilience.CircuitBreakerFactory
import com.marcosjordao.resiliencepoc.thirdparty.ibge.api.fixture.IbgeLocationCityRequestFixture
import com.marcosjordao.resiliencepoc.thirdparty.ibge.api.fixture.IbgeLocationCityResponseFixture
import com.marcosjordao.resiliencepoc.thirdparty.ibge.api.response.IbgeLocationCityResponse
import com.marcosjordao.resiliencepoc.thirdparty.ibge.config.IbgeLocationHttpClientConfiguration
import com.marcosjordao.resiliencepoc.thirdparty.ibge.exception.IbgeLocationException
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.core.ParameterizedTypeReference
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
internal class IbgeLocationCityClientTest {

    private lateinit var client: IbgeLocationCityClient

    @MockK
    private lateinit var config: IbgeLocationHttpClientConfiguration

    @MockK
    private lateinit var circuitBreakerFactory: CircuitBreakerFactory

    @MockK
    private lateinit var webClient: WebClient

    @BeforeEach
    fun setUp() {
        coEvery { config.buildClient(any()) } returns webClient
        coEvery { circuitBreakerFactory.buildCircuitBreaker(any()) } returns CircuitBreaker.ofDefaults("name")

        client = IbgeLocationCityClient(config, circuitBreakerFactory)
    }

    @Test
    fun `should return getCities successfully`() = runBlockingTest {
        val webClientResponse = mockk<WebClient.ResponseSpec>()
        val request = IbgeLocationCityRequestFixture.defaultIbgeLocationCityRequest()
        val expectedResponse = listOf(IbgeLocationCityResponseFixture.defaultIbgeLocationCityResponse())

        coEvery {
            webClientResponse.bodyToMono(object : ParameterizedTypeReference<List<IbgeLocationCityResponse>>() {})
        } returns Mono.just(expectedResponse)

        mockWebClient(webClientResponse)

        val response = client.getCities(request)

        assertAll(
            { assertEquals(response, expectedResponse) }
        )
    }

    @Test
    fun `should return IbgeLocationException when an unexpected error occurs on getCities`() = runBlockingTest {
        val webClientResponse = mockk<WebClient.ResponseSpec>()
        val request = IbgeLocationCityRequestFixture.defaultIbgeLocationCityRequest()

        coEvery {
            webClientResponse.bodyToMono(object : ParameterizedTypeReference<List<IbgeLocationCityResponse>>() {})
        } returns Mono.error(RuntimeException())

        mockWebClient(webClientResponse)

        assertThrows(IbgeLocationException::class.java) {
            runBlocking {
                client.getCities(request)
            }
        }
    }

    private fun mockWebClient(response: WebClient.ResponseSpec) {
        val spec = mockk<WebClient.RequestBodyUriSpec>()

        coEvery { spec.retrieve() } returns response

        coEvery { webClient.get().uri(any<String>()) } returns spec
    }
}
