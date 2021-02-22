package com.marcosjordao.resiliencepoc.common.resilience

import io.github.resilience4j.retry.Retry
import io.github.resilience4j.retry.RetryConfig
import io.github.resilience4j.retry.RetryRegistry
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class RetryFactory(
    private val config: RetryConfiguration
) {

    private companion object {
        private val log = KotlinLogging.logger { RetryFactory::class.java }
    }

    fun buildRetry(name: String, maxAttempts: Int? = null, waitDuration: Long? = null): Retry {
        val retryMaxAttempts = maxAttempts ?: config.maxAttempts
        val retryWaitDuration = waitDuration ?: config.waitDuration

        val config = RetryConfig.custom<RetryConfig>()
            .maxAttempts(retryMaxAttempts)
            .waitDuration(Duration.ofMillis(retryWaitDuration))
            .build()

        val registry = RetryRegistry.of(config)

        val retry = registry.retry(name)
        retry.eventPublisher
            .onEvent {
                log.info { "Retry event '${it.eventType}' [name=${it.name}; attempts={${it.numberOfRetryAttempts}/${retryMaxAttempts}}]" }
            }

        return retry
    }

}
