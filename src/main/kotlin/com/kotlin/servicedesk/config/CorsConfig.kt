package com.kotlin.servicedesk.config

import org.springframework.context.annotation.Bean
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.stereotype.Component

@Component
class CorsConfig(
    private val applicationFrontendProperties: ApplicationFrontendProperties
) {
    @Bean
    fun corsConfigurer(): WebMvcConfigurer? {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry
                    .addMapping(applicationFrontendProperties.corsPathPattern)
                    .allowedOrigins(applicationFrontendProperties.corsOrigins)
            }
        }
    }
}
