package com.zigzag.jobotaserver.features.job.mapper

import com.zigzag.jobotaserver.core.mapper.SimpleMapper
import com.zigzag.jobotaserver.features.job.database.PlatformJob
import com.zigzag.jobotaserver.features.job.dto.NewPlatformJobDto
import org.mapstruct.Mapper

/**
 *
 * @author  Roman Zinchuk
 */
@Mapper(componentModel = "spring")
interface NewJobMapper : SimpleMapper<PlatformJob,NewPlatformJobDto>