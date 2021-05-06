package com.zigzag.jobotaserver.user.database

import com.mongodb.lang.NonNull
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
)
