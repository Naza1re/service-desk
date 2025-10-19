package com.kotlin.servicedesk

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ServiceDeskApplication

fun main(args: Array<String>) {
    runApplication<ServiceDeskApplication>(*args)
}
