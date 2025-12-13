package com.kotlin.servicedesk

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EntityScan(
    basePackages = [
        "com.kotlin.servicedesk.model",
        "com.kotlin.entityframework.model.entity"
    ]
)
@EnableJpaRepositories(basePackages = ["com.kotlin.servicedesk.repository"])
class ServiceDeskApplication

fun main(args: Array<String>) {
    runApplication<ServiceDeskApplication>(*args)
}
