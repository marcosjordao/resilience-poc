package com.marcosjordao.resiliencepoc.common.resilience

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class CircuitBreakerFactory(
    private val config: CircuitBreakerConfiguration
) {

    private companion object {
        private val log = KotlinLogging.logger { CircuitBreakerFactory::class.java }
    }

    fun buildCircuitBreaker(
        name: String,
        slidingWindowSize: Int? = null,
        failureRateThreshold: Float? = null,
        slowCallRateThreshold: Float? = null,
        slowCallDurationThreshold: Long? = null,
        waitDurationInOpenState: Long? = null
    ): CircuitBreaker {
        val paramSlidingWindowSize = slidingWindowSize ?: config.slidingWindowSize
        val paramFailureRateThreshold = failureRateThreshold ?: config.failureRateThreshold
        val paramSlowCallRateThreshold = slowCallRateThreshold ?: config.slowCallRateThreshold
        val paramSlowCallDurationThreshold = slowCallDurationThreshold ?: config.slowCallDurationThreshold
        val paramWaitDurationInOpenState = waitDurationInOpenState ?: config.waitDurationInOpenState

        val config = CircuitBreakerConfig.custom()
            .slidingWindowType(SlidingWindowType.COUNT_BASED)
            .slidingWindowSize(paramSlidingWindowSize)
            .failureRateThreshold(paramFailureRateThreshold)
            .slowCallRateThreshold(paramSlowCallRateThreshold)
            .slowCallDurationThreshold(Duration.ofMillis(paramSlowCallDurationThreshold))
            .waitDurationInOpenState(Duration.ofMillis(paramWaitDurationInOpenState))
            .build()

        val registry = CircuitBreakerRegistry.of(config)

        val circuitBreaker = registry.circuitBreaker(name)
        circuitBreaker.eventPublisher.onStateTransition {
            log.info { "Circuit breaker '${it.eventType.name}' [name=${it.circuitBreakerName}; from=${it.stateTransition.fromState}; to=${it.stateTransition.toState}]" }
        }.onEvent {
            log.debug { "Circuit breaker event '${it.eventType}' [name=${it.circuitBreakerName}]" }
        }

        return circuitBreaker
    }

}
