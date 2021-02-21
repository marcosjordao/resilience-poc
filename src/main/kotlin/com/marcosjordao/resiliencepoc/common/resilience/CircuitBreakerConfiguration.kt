package com.marcosjordao.resiliencepoc.common.resilience

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "resilience.circuit-breaker")
data class CircuitBreakerConfiguration(
    val slidingWindowSize: Int,
    val failureRateThreshold: Float,
    val slowCallRateThreshold: Float,
    val slowCallDurationThreshold: Long,
    val waitDurationInOpenState: Long
)