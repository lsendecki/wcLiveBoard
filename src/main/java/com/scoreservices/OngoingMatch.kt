package com.scoreservices

import java.time.LocalDateTime
import java.time.ZoneOffset

data class OngoingMatch(
    val homeTeam: Team,
    val awayTeam: Team,
    val gameStartedAt: LocalDateTime = LocalDateTime.now()
) {
    val homeName get() = this.homeTeam.name
    val awayName get() = this.awayTeam.name

    val homeScore get() = homeTeam.goalsScored
    val awayScore get() = awayTeam.goalsScored

    fun totalScore() = homeTeam.goalsScored + awayTeam.goalsScored
    fun timeIndicator() =  gameStartedAt.toInstant(ZoneOffset.UTC).toEpochMilli()

    override fun toString() = "${homeTeam.name}: ${homeScore} - ${awayTeam.name}: ${awayScore}"
}