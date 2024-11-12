package com.sendecki

import java.time.LocalDateTime

class Scoreboard(val ongoingMatches: MutableList<OngoingMatch> = mutableListOf()) {

    constructor(ongoingMatch: OngoingMatch) : this(mutableListOf(ongoingMatch))

    fun addMatch(homeTeam: Team, awayTeam: Team, startsAt: LocalDateTime) {
        ongoingMatches.add(
            OngoingMatch(
                homeTeam=homeTeam,
                awayTeam=awayTeam,
                gameStartedAt = startsAt))
    }

    fun updateScore(homeTeam: Team, awayTeam: Team) {
        getMatchByTeams(homeTeam, awayTeam)
            ?.let {
                val matchUpdated = it.copy(homeTeam = homeTeam, awayTeam = awayTeam)
                ongoingMatches.remove(it)
                ongoingMatches.add(matchUpdated)
            }
    }

    fun finishGame(match: OngoingMatch) {
        TODO("Add finish game implementation ")
    }

    fun getSummary() =
        ongoingMatches.sortedWith(
            compareBy(
                { - it.totalScore() },
                { - it.timeIndicator() }
            )
        )

    fun active() = ongoingMatches.isNotEmpty()

    fun matchesCount() = ongoingMatches.size

    fun getMatchByTeams(homeTeam: Team, awayTeam: Team): OngoingMatch? {
        return ongoingMatches
            .find {
                it.homeTeam.name == homeTeam.name
                        && it.awayTeam.name == awayTeam.name
            }
    }
}