package com.kotlin.servicedesk.service.impl

import com.kotlin.servicedesk.config.properties.S3Properties
import com.kotlin.servicedesk.service.FileService
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetObjectRequest

@Service
class FileServiceImpl(
    private val s3Client: S3Client,
    s3Properties: S3Properties
) : FileService {
    private val bucketName = s3Properties.standardBucket

    override fun getFileByKey(s3fileKey: String): ByteArray {
        val request = GetObjectRequest.builder()
            .bucket(bucketName)
            .key(s3fileKey)
            .build()
        return s3Client.getObject(request).readAllBytes()
    }
}