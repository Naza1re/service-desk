package com.kotlin.servicedesk.dto

data class FileWithMeta(
    val bytes: ByteArray,
    val originalFilename: String,
    val contentType: String,
    val size: Long
)
