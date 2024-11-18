package com.sendecki

import java.time.LocalDateTime

// thread-unsafe solution based on mutable collections as a storage
class ScoreboardWriteService(private val scoreboardRepo: ScoreboardRepo) {

    fun addMatch(homeTeam: Team, awayTeam: Team, startsAt: LocalDateTime = LocalDateTime.now()) {
        scoreboardRepo.getStorage().add(OngoingMatch(
            homeTeam = homeTeam,
            awayTeam = awayTeam,
            gameStartedAt = startsAt)
        )
    }

    fun updateScore(ongoingMatch: OngoingMatch, newHomeScore: Int, newAwayScore: Int) {
        val updatedHomeTeam = ongoingMatch.homeTeam.copy(goalsScored = newHomeScore)
        val updatedAwayTeam = ongoingMatch.awayTeam.copy(goalsScored = newAwayScore)
        val matchUpdated = ongoingMatch.copy(homeTeam = updatedHomeTeam, awayTeam = updatedAwayTeam)
        scoreboardRepo.getStorage().remove(ongoingMatch)
        scoreboardRepo.getStorage().add(matchUpdated)
    }

    fun finishGame(game: OngoingMatch) {
        scoreboardRepo.getStorage().remove(game)
            .let {
                if (it) { // ughh... kinda ugly :))
                 println("The match between ${game.homeTeam} and ${game.awayTeam} was removed")
            }
        }
    }
}