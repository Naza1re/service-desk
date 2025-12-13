package com.kotlin.servicedesk.service.impl

import com.kotlin.entityframework.exception.EntityNotFoundException
import com.kotlin.entityframework.repository.entity.EntityRepository
import com.kotlin.servicedesk.config.properties.S3Properties
import com.kotlin.servicedesk.dto.EntityFilesResponse
import com.kotlin.servicedesk.dto.FileResponse
import com.kotlin.servicedesk.dto.FileWithMeta
import com.kotlin.servicedesk.exception.FileNotFoundException
import com.kotlin.servicedesk.model.FileEntity
import com.kotlin.servicedesk.repository.FileEntityRepository
import com.kotlin.servicedesk.service.FileService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest
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
    override fun getFileByKey(s3fileKey: String): FileWithMeta {
        val fileEntity = fileEntityRepository.findByKey(s3fileKey)
            ?: throw FileNotFoundException("File with key $s3fileKey not found")

        val request = GetObjectRequest.builder()
            .bucket(bucketName)
            .key(s3fileKey)
            .build()

        val s3object = s3Client.getObject(request)
        return FileWithMeta(
            bytes = s3object.readBytes(),
            originalFilename = fileEntity.key,
            contentType = fileEntity.contentType,
            size = fileEntity.size,
        )

    }

    @Transactional
    override fun uploadFileToEntity(file: MultipartFile, entityNumber: String): FileResponse {
        val entity = entityRepository.findByNumber(entityNumber)
            ?: throw EntityNotFoundException("Entity with number $entityNumber not found")
        val key = UUID.randomUUID().toString() + "-" + file.originalFilename
        val fileEntity = FileEntity(
                id = null,
                key = key,
                contentType = file.contentType ?: "application/octet-stream",
                size = file.size,
                entity = entity

        )
        val savedEntity = fileEntityRepository.save(fileEntity)
        val request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(file.contentType)
                .build()
            s3Client.putObject(request, RequestBody.fromBytes(file.bytes))
            return FileResponse(
                name = key,
                message = "File saved with key : $key to entity with number : ${entity.number}",)
    }

    override fun getAllEntityFiles(entityNumber: String): EntityFilesResponse {
        val listOfFiles = fileEntityRepository.findAllByEntity(entityNumber).map { it.key }
        return EntityFilesResponse(
            entityNumber = entityNumber,
            listOfFiles
        )
    }

    override fun deleteFileByKey(s3FileKey: String) {
        val file = fileEntityRepository.findByKey(s3FileKey)
        ?: throw FileNotFoundException("File with key $s3FileKey not found")
        fileEntityRepository.delete(file)

        s3Client.deleteObject(
            DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(s3FileKey)
                .build()
        )
    }


}