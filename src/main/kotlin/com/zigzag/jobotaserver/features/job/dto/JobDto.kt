package com.zigzag.jobotaserver.features.job.dto

import com.zigzag.jobotaserver.features.user.dto.PlatformUserDto

data class JobDto (
        val id:String,
        val jobName:String,
        val author: PlatformUserDto
)