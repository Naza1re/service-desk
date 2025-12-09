package com.kotlin.servicedesk.config.s3

import com.kotlin.servicedesk.config.properties.S3Properties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.S3Configuration
import java.net.URI

@Configuration
class S3Configuration(
    private val s3Properties: S3Properties
) {

    @Bean
    fun s3Client(): S3Client {
        val credentials = AwsBasicCredentials.create(s3Properties.accessKeyId,s3Properties.secretKey)

        return S3Client.builder()
            .endpointOverride(URI.create(s3Properties.url))
            .region(Region.US_EAST_1)
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .serviceConfiguration(
                S3Configuration.builder()
                .pathStyleAccessEnabled(true)
                .build())
            .httpClientBuilder(UrlConnectionHttpClient.builder())
            .build()
    }
}