package com.zigzag.jobotaserver.job.mapper

import com.zigzag.jobotaserver.core.mapper.SimpleMapper
import com.zigzag.jobotaserver.job.database.Job
import com.zigzag.jobotaserver.job.dto.NewJobDto
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface NewJobMapper : SimpleMapper<Job,NewJobDto> {
}