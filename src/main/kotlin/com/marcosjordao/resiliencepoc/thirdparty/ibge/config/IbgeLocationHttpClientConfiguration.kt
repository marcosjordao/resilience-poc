package com.marcosjordao.resiliencepoc.thirdparty.ibge.config

import com.marcosjordao.resiliencepoc.thirdparty.config.HttpClientConfiguration
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "ibge.location")
class IbgeLocationHttpClientConfiguration(
    url: String,
    connectTimeout: Int,
    connectionWriteTimeout: Long,
    connectionReadTimeout: Long
) : HttpClientConfiguration(url, connectTimeout, connectionWriteTimeout, connectionReadTimeout)
