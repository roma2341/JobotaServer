package com.zigzag.jobotaserver.core.helpers

import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class DateHelper : IDateHelper {
    override fun now(): LocalDateTime {
       return LocalDateTime.now();
    }
}