package com.zigzag.jobotaserver.features.user.database

import com.mongodb.lang.NonNull
import com.zigzag.jobotaserver.core.database.AbstractAuditingEntity
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

/**
 *
 * @author  Roman Zinchuk
 */
@Document(collection = "users")
data class PlatformUser (
    @Id
    var id: String? = null,

    @Indexed(unique=true)
    @NonNull
    val email:String,

    var firstName: String? = null,
    var lastName: String? = null ,
    var password: String? = null,
    var roles: List<String>? = null
) : AbstractAuditingEntity()
