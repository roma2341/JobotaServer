package com.zigzag.jobotaserver.features.job.dto

import com.zigzag.jobotaserver.features.user.dto.PlatformUserDto

/**
 *
 * @author  Roman Zinchuk
 */
data class PlatformJobDto (
        val id:String?,
        val name:String?,
        val description:String?,
        val author: PlatformUserDto?,
        val executor: PlatformUserDto?
)