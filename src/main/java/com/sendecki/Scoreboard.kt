package com.sendecki

import java.time.LocalDateTime

// thread-unsafe solution based on lists as a storage
class Scoreboard(val ongoingMatches: MutableList<OngoingMatch> = mutableListOf()) {

    constructor(ongoingMatch: OngoingMatch) : this(mutableListOf(ongoingMatch))

    fun addMatch(homeTeam: Team, awayTeam: Team, startsAt: LocalDateTime = LocalDateTime.now()) {
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
        getMatchByTeams(match.homeTeam, match.awayTeam)
            ?.let {
                ongoingMatches.remove(it)
                println("The match between ${match.homeTeam} and ${match.awayTeam} was removed")
            }
    }

    fun getSummary() =
        ongoingMatches.sortedWith(
            compareBy(
                { - it.totalScore() },
                { - it.timeIndicator() }
            )
        )

    fun active() = ongoingMatches.isNotEmpty()

    fun countMatches() = ongoingMatches.size

    fun getMatchByTeams(homeTeam: Team, awayTeam: Team): OngoingMatch? {
        return ongoingMatches
            .find { it.homeTeam.name == homeTeam.name &&
                    it.awayTeam.name == awayTeam.name
            }
    }
}