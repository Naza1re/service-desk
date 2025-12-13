package com.kotlin.servicedesk.service.impl

import com.kotlin.entityframework.exception.EntityNotFoundException
import com.kotlin.entityframework.repository.entity.EntityRepository
import com.kotlin.servicedesk.config.properties.S3Properties
import com.kotlin.servicedesk.dto.EntityFilesResponse
import com.kotlin.servicedesk.dto.FileResponse
import com.kotlin.servicedesk.model.FileEntity
import com.kotlin.servicedesk.repository.FileEntityRepository
import com.kotlin.servicedesk.service.FileService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.util.*

@Service
class FileServiceImpl(
    private val s3Client: S3Client,
    s3Properties: S3Properties,
    private val entityRepository: EntityRepository,
    private val fileEntityRepository : FileEntityRepository
) : FileService {
    private val bucketName = s3Properties.standardBucket

    @Transactional(readOnly = true)
    override fun getFileByKey(s3fileKey: String): ByteArray {
        val request = GetObjectRequest.builder()
            .bucket(bucketName)
            .key(s3fileKey)
            .build()
        return s3Client.getObject(request).readAllBytes()
    }

    @Transactional
    override fun uploadFileToEntity(file: MultipartFile, entityNumber: String): FileResponse {
        val entity = entityRepository.findByNumber(entityNumber)
        if (entity != null) {
            val key = UUID.randomUUID().toString() + "-" + file.originalFilename
            val request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(file.contentType)
                .build()
            s3Client.putObject(request, RequestBody.fromBytes(file.bytes))
            val fileEntity = FileEntity(
                id = 0,
                key = key,
                size = file.size,
                entity = entity
            )
            val savedEntity = fileEntityRepository.save(fileEntity)
            return FileResponse(
                name = key,
                message = "File saved with key : $key to entity with number : $savedEntity"
            )
        } else {
            throw EntityNotFoundException("Entity with number $entityNumber not found")
        }
    }

    override fun getAllEntityFiles(entityId: String): EntityFilesResponse {
        TODO("Not yet implemented")
    }

    override fun deleteFileByKey(s3FileKey: String) {
        TODO("Not yet implemented")
    }


}