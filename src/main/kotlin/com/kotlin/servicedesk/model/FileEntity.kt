package com.kotlin.servicedesk.model

import jakarta.persistence.*

@Table(name = "file_entity")
@Entity
data class FileEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "file_key")
    val key: String,
    @Column(name = "size")
    val size: Long,
    @ManyToOne
    @JoinColumn(name = "entity_id", nullable = false)
    val entity: com.kotlin.entityframework.model.entity.Entity

)