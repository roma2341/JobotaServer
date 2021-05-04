package com.zigzag.jobotaserver.user.mapper

import com.zigzag.jobotaserver.core.mapper.SimpleMapper
import com.zigzag.jobotaserver.user.database.PlatformUser
import com.zigzag.jobotaserver.user.dto.PlatformUserDto
import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface PlatformUserMapper : SimpleMapper<PlatformUser,PlatformUserDto> {

}