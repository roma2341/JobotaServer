package com.zigzag.jobotaserver.features.job.mapper

import com.zigzag.jobotaserver.core.mapper.SimpleMapper
import com.zigzag.jobotaserver.features.job.database.PlatformJob
import com.zigzag.jobotaserver.features.job.dto.PlatformJobDto
import org.mapstruct.Mapper

/**
 *
 * @author  Roman Zinchuk
 */
@Mapper(componentModel = "spring")
interface JobMapper : SimpleMapper<PlatformJob,PlatformJobDto>