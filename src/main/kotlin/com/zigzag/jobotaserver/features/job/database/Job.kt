package com.zigzag.jobotaserver.features.job.database

import com.zigzag.jobotaserver.features.user.database.PlatformUser
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection="job")
class Job (
    @Id
    val id:String,
    val jobName:String,
    @DBRef
    val author:PlatformUser
)
