package com.kotlin.servicedesk.config.s3

import com.kotlin.servicedesk.config.properties.S3Properties
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.s3.S3Client

@Component
class S3BucketInitializer(
    private val s3Client: S3Client,
    private val s3Properties: S3Properties
) {

    @PostConstruct
    fun init() {
        val bucketName = s3Properties.standardBucket

        if (!s3Client.listBuckets().buckets().any { it.name() == bucketName }) {
            s3Client.createBucket {
                it.bucket(bucketName)
            }
        }
    }
}
