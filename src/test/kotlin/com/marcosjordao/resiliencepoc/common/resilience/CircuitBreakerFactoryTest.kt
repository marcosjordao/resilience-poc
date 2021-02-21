package com.marcosjordao.resiliencepoc.common.resilience

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import java.time.Duration

@ExperimentalCoroutinesApi
@TestInstance(Lifecycle.PER_CLASS)
internal class CircuitBreakerFactoryTest {

    companion object {
        const val DEFAULT_NAME = "test"
    }

    private lateinit var circuitBreakerFactory: CircuitBreakerFactory

    @BeforeAll
    fun setUp() {
        circuitBreakerFactory = CircuitBreakerFactory(defaultCircuitBreakerConfiguration())
    }

    @Test
    fun `should build a default circuit breaker`() = runBlockingTest {
        val circuitBreaker = circuitBreakerFactory.buildCircuitBreaker(DEFAULT_NAME)

        assertEquals(DEFAULT_NAME, circuitBreaker.name)
    }

    @Test
    fun `should build a parameterized circuit breaker`() = runBlockingTest {
        val circuitBreaker =
            circuitBreakerFactory.buildCircuitBreaker(
                DEFAULT_NAME,
                slidingWindowSize = 1,
                failureRateThreshold = 2F,
                slowCallRateThreshold = 3F,
                slowCallDurationThreshold = 4,
                waitDurationInOpenState = 5
            )

        assertAll(
            { assertEquals(DEFAULT_NAME, circuitBreaker.name) },
            { assertEquals(1, circuitBreaker.circuitBreakerConfig.slidingWindowSize) },
            { assertEquals(2F, circuitBreaker.circuitBreakerConfig.failureRateThreshold) },
            { assertEquals(3F, circuitBreaker.circuitBreakerConfig.slowCallRateThreshold) },
            { assertEquals(Duration.ofMillis(4), circuitBreaker.circuitBreakerConfig.slowCallDurationThreshold) },
            { assertEquals(Duration.ofMillis(5), circuitBreaker.circuitBreakerConfig.waitDurationInOpenState) }
        )
    }

    private fun defaultCircuitBreakerConfiguration() =
        CircuitBreakerConfiguration(
            slidingWindowSize = 10,
            failureRateThreshold = 50F,
            slowCallDurationThreshold = 1000,
            slowCallRateThreshold = 50F,
            waitDurationInOpenState = 1000
        )

}
