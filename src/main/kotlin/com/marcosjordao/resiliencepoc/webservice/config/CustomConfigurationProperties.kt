package com.marcosjordao.resiliencepoc.webservice.config

import com.marcosjordao.resiliencepoc.common.resilience.CircuitBreakerConfiguration
import com.marcosjordao.resiliencepoc.common.resilience.RetryConfiguration
import com.marcosjordao.resiliencepoc.thirdparty.ibge.config.IbgeLocationHttpClientConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(
    value = [
        RetryConfiguration::class,
        CircuitBreakerConfiguration::class,
        IbgeLocationHttpClientConfiguration::class
    ]
)
class CustomConfigurationProperties