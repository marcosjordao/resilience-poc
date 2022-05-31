package com.marcosjordao.resiliencepoc.thirdparty.ibge.client

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.marcosjordao.resiliencepoc.common.resilience.RetryFactory
import com.marcosjordao.resiliencepoc.thirdparty.ibge.api.fixture.IbgeLocationStateResponseFixture
import com.marcosjordao.resiliencepoc.thirdparty.ibge.api.response.IbgeLocationStateResponse
import com.marcosjordao.resiliencepoc.thirdparty.ibge.config.IbgeLocationHttpClientConfiguration
import com.marcosjordao.resiliencepoc.thirdparty.ibge.exception.IbgeLocationException
import io.github.resilience4j.retry.Retry
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
internal class IbgeLocationStateClientTest {

    private lateinit var client: IbgeLocationStateClient

    private val objectMapper = jacksonObjectMapper()

    @MockK
    private lateinit var config: IbgeLocationHttpClientConfiguration

    @MockK
    private lateinit var retryFactory: RetryFactory

    @MockK
    private lateinit var webClient: WebClient

    @BeforeEach
    fun setUp() {
        coEvery { config.buildClient(any()) } returns webClient
        coEvery { retryFactory.buildRetry(any()) } returns Retry.ofDefaults("name")

        client = IbgeLocationStateClient(config, retryFactory, objectMapper)
    }

    @Test
    fun `should return getStates successfully`() = runBlockingTest {
        val webClientResponse = mockk<WebClient.ResponseSpec>()
        val expectedResponse = listOf(IbgeLocationStateResponseFixture.defaultIbgeLocationStateResponse())

        coEvery {
            webClientResponse.bodyToMono(object : ParameterizedTypeReference<List<IbgeLocationStateResponse>>() {})
        } returns Mono.just(expectedResponse)

        mockWebClient(webClientResponse)

        val response = client.getStates()

        assertAll(
            { assertEquals(response, expectedResponse) }
        )
    }

    @Test
    fun `should return IbgeLocationException when an unexpected error occurs on getStates`() = runBlockingTest {
        val webClientResponse = mockk<WebClient.ResponseSpec>()

        coEvery {
            webClientResponse.bodyToMono(object : ParameterizedTypeReference<List<IbgeLocationStateResponse>>() {})
        } returns Mono.error(RuntimeException())

        mockWebClient(webClientResponse)

        assertThrows(IbgeLocationException::class.java) {
            runBlocking {
                client.getStates()
            }
        }
    }

    private fun mockWebClient(response: WebClient.ResponseSpec) {
        val spec = mockk<WebClient.RequestBodyUriSpec>()

        coEvery { spec.retrieve() } returns response

        coEvery { webClient.get().uri(IbgeLocationStateClient.API_URI) } returns spec
    }
}
