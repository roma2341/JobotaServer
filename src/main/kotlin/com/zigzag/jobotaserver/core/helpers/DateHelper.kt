package com.zigzag.jobotaserver.core.helpers

import org.springframework.stereotype.Service
import java.time.LocalDateTime

/**
 * Store date functions to improve testing experience
 * @author  Roman Zinchuk
 */
@Service
class DateHelper : IDateHelper {
    override fun now(): LocalDateTime {
       return LocalDateTime.now();
    }
}