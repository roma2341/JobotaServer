package com.zigzag.jobotaserver.user.mapper

import com.zigzag.jobotaserver.core.mapper.SimpleMapper
import com.zigzag.jobotaserver.user.database.PlatformUser
import com.zigzag.jobotaserver.user.dto.NewPlatformUserDto
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface NewPlatformUserMapper : SimpleMapper<PlatformUser,NewPlatformUserDto>