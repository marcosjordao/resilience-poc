package com.marcosjordao.resiliencepoc.webservice.config

import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kotlin.coroutine.EnableCoroutine

@Configuration
@EnableCaching
@EnableCoroutine
class CacheConfig {

    @Bean
    fun defaultCacheManager() =
        ConcurrentMapCacheManager()
}
