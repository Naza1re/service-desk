package com.kotlin.servicedesk.aspect

import com.kotlin.entityframework.dto.entity.response.EntityResponse
import com.kotlin.entityframework.repository.entity.EntityRepository
import com.kotlin.outboxstarter.service.OutboxMessageService
import com.kotlin.servicedesk.config.outbox.KafkaSDTopicConfig
import com.kotlin.servicedesk.kafka.EntityInformation
import com.kotlin.servicedesk.service.FileService
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

@Component
@Aspect
class EntityUpdateAspect(
    private val outboxMessageService: OutboxMessageService,
    private val fileService: FileService,
    private val entityRepository: EntityRepository,
    private val kafkaSDTopicConfig: KafkaSDTopicConfig
) {

    @Around(
        "execution(* com.kotlin.entityframework.service.entity.EntityServiceImpl.createEntity(" +
                "com.kotlin.entityframework.dto.entity.request.CreateRequest))"
    )
    fun aroundCreateEntity(pjp: ProceedingJoinPoint): Any? {

        val result = pjp.proceed()

        val entityResponse = result as EntityResponse
        uploadInformationToTopic(entityResponse, kafkaSDTopicConfig.entityCreate)
        return result
    }

    @Around(
        "execution(* com.kotlin.entityframework.service.entity.EntityServiceImpl.updateEntity(" +
                "com.kotlin.entityframework.model.entity.Entity, java.util.Map))"
    )
    fun aroundUpdateEntityInternal(pjp: ProceedingJoinPoint): Any? {

        val result = pjp.proceed()

        val entityResponse = result as EntityResponse
        uploadInformationToTopic(entityResponse, kafkaSDTopicConfig.entityUpdate)
        return result
    }

    @Around(
        "execution(* com.kotlin.entityframework.service.entity.EntityServiceImpl.updateEntity(" +
                "java.lang.String, com.kotlin.entityframework.dto.entity.request.UpdateRequest))"
    )
    fun aroundUpdateEntityApi(pjp: ProceedingJoinPoint): Any? {

        val result = pjp.proceed()

        val entityResponse = result as EntityResponse
        uploadInformationToTopic(entityResponse, kafkaSDTopicConfig.entityUpdate)
        return result
    }


    private fun uploadInformationToTopic(result: EntityResponse, topicName: String) {


        val entity = entityRepository.findByNumber(result.number)
        if (entity != null) {

            val entityInformation = EntityInformation(
                entity.number,
                entity.name,
                entity.description,
                entity.createdAt.toString(),
                entity.updatedAt.toString(),
                entity.properties ?: mapOf(),
                fileService.getAllEntityFiles(entity.number).fileNames
            )

            outboxMessageService.send(entityInformation, topicName);
        }
    }
}
