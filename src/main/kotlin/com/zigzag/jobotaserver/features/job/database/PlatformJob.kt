package com.zigzag.jobotaserver.features.job.database

import com.zigzag.jobotaserver.core.database.AbstractAuditingEntity
import com.zigzag.jobotaserver.features.user.database.PlatformUser
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

/**
 *
 * @author  Roman Zinchuk
 */
@Document(collection="job")
data class PlatformJob(
    @Id
    val id:String? = null,
    var name:String? = null,
    var description:String? = null,
    var completedAt: LocalDateTime? = null,
    @DBRef
    var executor:PlatformUser? = null,
    @DBRef
    var author:PlatformUser? = null,
) : AbstractAuditingEntity()
