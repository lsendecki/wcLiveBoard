package com.sendecki

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

data class OngoingMatch(
    val homeTeam: Team,
    val awayTeam: Team,
    val gameStartedAt: LocalDateTime = LocalDateTime.now(),
) {
    fun homeScore() = homeTeam.goalsScored
    fun awayScore() = awayTeam.goalsScored

    fun totalScore() = homeTeam.goalsScored + awayTeam.goalsScored
    fun timeIndicator() =  gameStartedAt.toInstant(ZoneOffset.UTC).toEpochMilli()

    override fun toString() = "${homeTeam.name}: ${homeScore()} - ${awayTeam.name}: ${awayScore()}"

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true;
        }
        if (other !is OngoingMatch) {
            return false;
        }
        return Objects.equals(homeTeam.name, other.homeTeam.name)
                && Objects.equals(awayTeam.name, other.awayTeam.name)
    }

    override fun hashCode(): Int {
        return Objects.hash(homeTeam.name, awayTeam.name)
    }
}