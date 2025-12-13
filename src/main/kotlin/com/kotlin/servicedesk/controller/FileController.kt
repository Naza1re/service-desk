package com.kotlin.servicedesk.controller

import com.kotlin.servicedesk.dto.EntityFilesResponse
import com.kotlin.servicedesk.dto.FileResponse
import com.kotlin.servicedesk.service.FileService
import org.springframework.http.HttpStatus
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
        return ResponseEntity.ok(fileService.getFileByKey(s3fileKey))
    }

    @PostMapping("/{entityId}")
    fun addFileToEntity(@RequestBody file: MultipartFile,
                        @PathVariable entityId: String) : ResponseEntity<FileResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(fileService.uploadFileToEntity(file, entityId))
    }

    @GetMapping("/entity/{entityId}")
    fun getAllFilesByEntityId(@PathVariable entityId: String) : ResponseEntity<EntityFilesResponse> {
        return ResponseEntity.ok(fileService.getAllEntityFiles(entityId))
    }

    @DeleteMapping("{s3FileKey}")
    fun deleteFileByKey(@PathVariable s3FileKey: String) : ResponseEntity<FileResponse>  {
        fileService.deleteFileByKey(s3FileKey)
        return ResponseEntity.noContent().build()
    }
}