package com.zigzag.jobotaserver.features.job.mapper

import com.zigzag.jobotaserver.core.mapper.SimpleMapper
import com.zigzag.jobotaserver.features.job.database.Job
import com.zigzag.jobotaserver.features.job.dto.NewJobDto
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface NewJobMapper : SimpleMapper<Job,NewJobDto>