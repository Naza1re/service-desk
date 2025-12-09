package com.kotlin.servicedesk.service

interface FileService {
    fun getFileByKey(s3fileKey: String): ByteArray
}