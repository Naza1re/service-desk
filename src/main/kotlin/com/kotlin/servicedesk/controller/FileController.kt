package com.kotlin.servicedesk.controller

import com.kotlin.servicedesk.dto.EntityFilesResponse
import com.kotlin.servicedesk.dto.FileResponse
import com.kotlin.servicedesk.service.FileService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v0.1/files")
class FileController(
    private val fileService: FileService
) {

    @GetMapping("/{s3fileKey}")
    fun getFileByKey(@PathVariable s3fileKey: String) : ResponseEntity<ByteArray> {

        val file = fileService.getFileByKey(s3fileKey)
        return ResponseEntity.ok()
            .header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"${file.originalFilename}\""
            )
            .contentType(MediaType.parseMediaType(file.contentType))
            .contentLength(file.size)
            .body(file.bytes)

    }

    @PostMapping("/{entityNumber}")
    fun addFileToEntity(@RequestBody file: MultipartFile,
                        @PathVariable entityNumber: String) : ResponseEntity<FileResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(fileService.uploadFileToEntity(file, entityNumber))
    }

    @GetMapping("/entity/{entityNumber}")
    fun getAllFilesByEntityId(@PathVariable entityNumber: String) : ResponseEntity<EntityFilesResponse> {
        return ResponseEntity.ok(fileService.getAllEntityFiles(entityNumber))
    }

    @DeleteMapping("/{s3FileKey}")
    fun deleteFileByKey(@PathVariable s3FileKey: String) : ResponseEntity<FileResponse>  {
        fileService.deleteFileByKey(s3FileKey)
        return ResponseEntity.noContent().build()
    }
}