package com.kotlin.servicedesk.service

import com.kotlin.servicedesk.dto.EntityFilesResponse
import com.kotlin.servicedesk.dto.FileResponse
import com.kotlin.servicedesk.dto.FileWithMeta
import org.springframework.web.multipart.MultipartFile

interface FileService {
    fun getFileByKey(s3fileKey: String): FileWithMeta
    fun uploadFileToEntity(file: MultipartFile, entityNumber: String): FileResponse
    fun getAllEntityFiles(entityId: String): EntityFilesResponse
    fun deleteFileByKey(s3FileKey: String)
}