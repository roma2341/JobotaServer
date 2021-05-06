package com.zigzag.jobotaserver.job.mapper

import com.zigzag.jobotaserver.core.mapper.SimpleMapper
import com.zigzag.jobotaserver.job.database.Job
import com.zigzag.jobotaserver.job.dto.JobDto
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface JobMapper : SimpleMapper<Job,JobDto>