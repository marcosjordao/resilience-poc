package com.marcosjordao.resiliencepoc.webservice.config

import com.marcosjordao.resiliencepoc.thirdparty.ibge.config.IbgeLocationHttpClientConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(
    value = [IbgeLocationHttpClientConfiguration::class]
)
class CustomConfigurationProperties