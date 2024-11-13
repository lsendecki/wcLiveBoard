package com.sendecki

class ScoreboardReadService(private val scoreboardRepo: ScoreboardRepo) {

    fun getSummary() =
        scoreboardRepo.getStorage().sortedWith(
            compareBy(
                { - it.totalScore() },
                { - it.timeIndicator() }
            )
        )

    fun isActive() = scoreboardRepo.getStorage().isNotEmpty()

    fun countMatches() = scoreboardRepo.getStorage().size

    fun getMatchByTeams(homeTeam: Team, awayTeam: Team) =
        scoreboardRepo.getStorage()
            .find { it.homeTeam.name == homeTeam.name
                    && it.awayTeam.name == awayTeam.name
            }
}