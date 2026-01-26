package com.kotlin.servicedesk.config.outbox

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Component
@Configuration
@ConfigurationProperties(prefix = "outbox.kafka.topics")
class KafkaSDTopicConfig {

    lateinit var entityCreate: String
    lateinit var entityUpdate: String
    lateinit var entityDelete: String

    @Bean
    fun entityCreateTopic() = NewTopic(entityCreate, 1, 1)

    @Bean
    fun entityUpdateTopic() = NewTopic(entityUpdate, 1, 1)

    @Bean
    fun entityDeleteTopic() = NewTopic(entityDelete, 1, 1)
}
