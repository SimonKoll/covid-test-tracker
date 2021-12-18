package at.htl.leonding.coronatesttracker.Model

import java.time.LocalDateTime


data class Report(
    val id: String,
    val dateAndTime: LocalDateTime,
    val isPositive: Boolean,
    val office: String
)
