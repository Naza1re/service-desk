package com.kotlin.servicedesk.repository

import com.kotlin.servicedesk.model.FileEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FileEntityRepository : JpaRepository<FileEntity, Long> {
}