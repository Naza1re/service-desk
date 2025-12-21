package com.kotlin.servicedesk.config.kafka

import com.fasterxml.jackson.annotation.JsonProperty
import com.kotlin.outboxstarter.kafka.KafkaObject

class EntityInformation(
    @JsonProperty("number") val number: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("createdAt") val createdAt: String,
    @JsonProperty("updatedAt") val updatedAt: String,
    @JsonProperty("properties") val properties: Map<String, Any>
) : KafkaObject<EntityInformation> {
    override fun key(): String {
        return number
    }

    override fun value(): EntityInformation {
        return this
    }
}