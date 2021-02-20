package com.marcosjordao.resiliencepoc.common.resilience

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "resilience.retry")
class RetryConfiguration(
    val maxAttempts: Int,
    val waitDuration: Long
)