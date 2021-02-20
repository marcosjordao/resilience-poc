package com.marcosjordao.resiliencepoc.common.resilience

import io.github.resilience4j.retry.Retry
import io.github.resilience4j.retry.RetryConfig
import io.github.resilience4j.retry.RetryRegistry
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class RetryFactory(
    @Value("\${resilience.retry.maxAttempts}") private val maxAttempts: Int,
    @Value("\${resilience.retry.waitDuration}") private val waitDuration: Long
) {

    private companion object {
        private val log = KotlinLogging.logger { RetryFactory::class.java }
    }

    fun buildRetry(name: String): Retry {
        val config = RetryConfig.custom<RetryConfig>()
            .maxAttempts(maxAttempts)
            .waitDuration(Duration.ofMillis(waitDuration))
            .build()

        val registry = RetryRegistry.of(config)

        val retry = registry.retry(name)
        retry.eventPublisher
            .onEvent {
                log.info { "Retry event '${it.eventType}' [name=${it.name}; attempts={${it.numberOfRetryAttempts}/$maxAttempts}]" }
            }

        return retry
    }

}