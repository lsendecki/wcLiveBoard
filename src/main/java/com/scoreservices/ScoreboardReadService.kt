package com.scoreservices

class ScoreboardReadService(private val scoreboardRepo: ScoreboardRepo) {

    fun getSummary() = scoreboardRepo.getStorage()

    fun isActive() = scoreboardRepo.getStorage().isNotEmpty()

    fun countMatches() = scoreboardRepo.getStorage().size

    fun getMatchByTeams(homeTeam: Team, awayTeam: Team) =
        scoreboardRepo.getStorage()
            .find { it.homeName == homeTeam.name
                    && it.awayName == awayTeam.name
            }
}