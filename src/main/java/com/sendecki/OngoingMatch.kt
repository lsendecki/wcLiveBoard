package com.sendecki

import java.time.LocalDateTime
import java.time.ZoneOffset

data class OngoingMatch(
    val homeTeam: Team,
    val awayTeam: Team,
    val gameStartedAt: LocalDateTime,
) {
    fun homeScore() = homeTeam.currentScore
    fun awayScore() = awayTeam.currentScore

    override fun toString() = "${homeTeam.name}: ${homeScore()} - ${awayTeam.name}: ${awayScore()}"
}