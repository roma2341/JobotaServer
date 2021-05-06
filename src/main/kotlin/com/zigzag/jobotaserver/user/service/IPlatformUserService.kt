package com.zigzag.jobotaserver.user.service

import com.zigzag.jobotaserver.core.ICrudService
import com.zigzag.jobotaserver.user.database.PlatformUser
import com.zigzag.jobotaserver.user.dto.NewPlatformUserDto
import reactor.core.publisher.Mono

interface IPlatformUserService : ICrudService<PlatformUser> {

}