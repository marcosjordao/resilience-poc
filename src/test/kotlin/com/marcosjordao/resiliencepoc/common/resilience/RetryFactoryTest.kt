package com.marcosjordao.resiliencepoc.common.resilience

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle

@ExperimentalCoroutinesApi
@TestInstance(Lifecycle.PER_CLASS)
internal class RetryFactoryTest {

    companion object {
        const val DEFAULT_NAME = "test"
    }

    private lateinit var retryFactory: RetryFactory

    @BeforeAll
    fun setUp() {
        retryFactory = RetryFactory(defaultRetryConfiguration())
    }

    @Test
    fun `should build a default retry`() = runBlockingTest {
        val retry = retryFactory.buildRetry(DEFAULT_NAME)

        assertEquals(DEFAULT_NAME, retry.name)
    }

    @Test
    fun `should build a parameterized retry`() = runBlockingTest {
        val retry =
            retryFactory.buildRetry(
                DEFAULT_NAME,
                maxAttempts = 1,
                waitDuration = 2
            )

        assertAll(
            { assertEquals(DEFAULT_NAME, retry.name) },
            { assertEquals(1, retry.retryConfig.maxAttempts) }
        )
    }

    private fun defaultRetryConfiguration() =
        RetryConfiguration(
            maxAttempts = 3,
            waitDuration = 100
        )

}
