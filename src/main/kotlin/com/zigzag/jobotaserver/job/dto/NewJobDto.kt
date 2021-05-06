package com.zigzag.jobotaserver.job.dto

import com.zigzag.jobotaserver.user.dto.PlatformUserDto

data class NewJobDto (
    val id:String,
    val jobName:String,
    val author: PlatformUserDto
)