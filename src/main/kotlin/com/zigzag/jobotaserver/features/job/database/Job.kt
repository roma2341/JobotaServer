package com.zigzag.jobotaserver.features.job.database

import com.mongodb.lang.NonNull
import com.zigzag.jobotaserver.core.database.AbstractAuditingEntity
import com.zigzag.jobotaserver.features.user.database.PlatformUser
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection="job")
data class Job(
    @Id
    val id:String?,
    @DBRef
    val author:PlatformUser,

    var name:String,
    var description:String,
    @DBRef
    var executor:PlatformUser,
) : AbstractAuditingEntity()
