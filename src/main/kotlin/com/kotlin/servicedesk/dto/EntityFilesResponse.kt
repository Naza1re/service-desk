package com.kotlin.servicedesk.dto

data class EntityFilesResponse(
    val entityId: String,
    val fileNames: List<String>
)