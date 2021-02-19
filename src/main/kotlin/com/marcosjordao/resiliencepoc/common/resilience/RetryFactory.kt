package com.marcosjordao.resiliencepoc.common.resilience

import io.github.resilience4j.retry.Retry
import io.github.resilience4j.retry.RetryConfig
import io.github.resilience4j.retry.RetryRegistry
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class RetryFactory(
    @Value("ibge.location.retryMaxAttempts") private val maxAttempts: Int,
    @Value("ibge.location.retryWaitDuration") private val waitDuration: Long
) {

    fun buildRetry(name: String): Retry {
        val config = RetryConfig.custom<RetryConfig>()
            .maxAttempts(maxAttempts)
            .waitDuration(Duration.ofMillis(waitDuration))
            .build()

        val registry = RetryRegistry.of(config)

        return registry.retry(name)
    }

}