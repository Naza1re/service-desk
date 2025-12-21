package com.kotlin.servicedesk.config.outbox

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.apache.kafka.clients.admin.NewTopic

@Configuration
class KafkaSDTopicConfig {

    @Bean
    fun entityTopic() : NewTopic {
        return NewTopic("entity-topic", 1, 1)
    }
}