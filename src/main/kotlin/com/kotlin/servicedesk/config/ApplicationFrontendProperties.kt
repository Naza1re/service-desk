package com.kotlin.servicedesk.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("frontend")
class ApplicationFrontendProperties {
    val corsOrigins: String = ""
    val corsPathPattern: String = ""
}
