package com.marcosjordao.resiliencepoc.thirdparty.ibge.config

import com.marcosjordao.resiliencepoc.thirdparty.config.HttpConnectionPoolConfiguration
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "ibge.location")
class IbgeLocationHttpPoolConfiguration(
    url: String,
    connectionWriteTimeout: Long,
    connectionReadTimeout: Long,
    connectTimeout: Int
) : HttpConnectionPoolConfiguration(url, connectionWriteTimeout, connectionReadTimeout, connectTimeout)
