package com.zigzag.jobotaserver.features.user.database

import com.mongodb.lang.NonNull
import com.zigzag.jobotaserver.core.database.AbstractAuditingEntity
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "users")
data class PlatformUser (
    @Id
    val id: String?,

    @Indexed(unique=true)
    @NonNull
    val email:String,

    val firstName: String?,
    val lastName: String?,
) : AbstractAuditingEntity()
