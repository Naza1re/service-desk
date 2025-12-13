package com.kotlin.servicedesk.dto

data class EntityFilesResponse(
    val entityNumber: String,
    val fileNames: List<String>
)