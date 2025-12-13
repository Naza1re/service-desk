package com.kotlin.servicedesk.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "s3.config")
class S3Properties {
    lateinit var accessKeyId: String
    lateinit var secretKey: String
    lateinit var url: String
    lateinit var standardBucket: String

}