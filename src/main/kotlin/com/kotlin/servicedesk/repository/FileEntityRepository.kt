package com.kotlin.servicedesk.repository

import com.kotlin.servicedesk.model.FileEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface FileEntityRepository : JpaRepository<FileEntity, Long> {
    fun findByKey(key: String): FileEntity?

    @Query("""
        FROM FileEntity
        WHERE entity.number = :entityNumber
    """)
    fun findAllByEntity(entityNumber: String) : List<FileEntity>
}