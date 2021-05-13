package com.zigzag.jobotaserver.core.mapper

import org.mapstruct.InheritInverseConfiguration

/**
 *
 * @author  Roman Zinchuk
 */
interface SimpleMapper<Model,Dto> {
    fun convertToDto(user: Model) : Dto
    @InheritInverseConfiguration
    fun convertToModel(userDto: Dto) : Model
}
