package com.zigzag.jobotaserver.features.job.mapper

import com.zigzag.jobotaserver.core.mapper.SimpleMapper
import com.zigzag.jobotaserver.features.job.database.Job
import com.zigzag.jobotaserver.features.job.dto.JobDto
import org.mapstruct.Mapper

/**
 *
 * @author  Roman Zinchuk
 */
@Mapper(componentModel = "spring")
interface JobMapper : SimpleMapper<Job,JobDto>