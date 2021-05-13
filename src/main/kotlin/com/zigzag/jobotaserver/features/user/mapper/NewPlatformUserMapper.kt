package com.zigzag.jobotaserver.features.user.mapper

import com.zigzag.jobotaserver.core.mapper.SimpleMapper
import com.zigzag.jobotaserver.features.user.database.PlatformUser
import com.zigzag.jobotaserver.features.user.dto.NewPlatformUserDto
import org.mapstruct.Mapper

/**
 *
 * @author  Roman Zinchuk
 */
@Mapper(componentModel = "spring")
interface NewPlatformUserMapper : SimpleMapper<PlatformUser,NewPlatformUserDto>