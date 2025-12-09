package com.kotlin.servicedesk.controller

import com.kotlin.servicedesk.service.FileService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v0.01/files")
class FileController(
    private val fileService: FileService
) {

    @GetMapping("/{s3fileKey}")
    fun getFileByKey(@PathVariable s3fileKey: String) : ResponseEntity<ByteArray> {
        return ResponseEntity.ok(fileService.getFileByKey(s3fileKey))
    }
}