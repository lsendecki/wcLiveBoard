package com.sendecki

import java.time.LocalDateTime

class Scoreboard(val ongoingMatches: MutableList<OngoingMatch>) {

    fun addMatch(homeTeam: Team, awayTeam: Team, startsAt: LocalDateTime) {
        ongoingMatches.add(
            OngoingMatch(
                homeTeam=homeTeam,
                awayTeam=awayTeam,
                gameStartedAt = startsAt))
    }

    fun updateScore(homeTeam: Team, awayTeamScore: Team) {
        TODO("Add update score implementation")
    }

    fun finishGame(match: OngoingMatch) {
        TODO("Add finish game implementation ")
    }

    fun getSummary() = listOf<OngoingMatch>() // TODO("Implement a proper logic")

    fun active() = ongoingMatches.isNotEmpty()

    fun getMatchByTeams(homeTeam: Team, awayTeam: Team): OngoingMatch {
        TODO("Add search by teams logic")
    }
}