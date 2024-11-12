package com.sendecki

import java.time.LocalDateTime

class ScoreboardService(
    private val scoreboardReadService: ScoreboardReadService,
    private val scoreboardWriteService: ScoreboardWriteService) {

    fun addMatch(homeTeam: Team, awayTeam: Team, startsAt: LocalDateTime = LocalDateTime.now()) {
        scoreboardWriteService.addMatch(homeTeam, awayTeam, startsAt)
    }

    fun updateScore(newHomeResult: Team, newAwayResult: Team) {
        scoreboardReadService.getMatchByTeams(newHomeResult, newAwayResult)
        ?.let {
            scoreboardWriteService.updateScore(
                it,
                newHomeResult.goalsScored,
                newAwayResult.goalsScored
            )
        }
    }

    fun finishGame(game: OngoingMatch) {
        scoreboardReadService.getMatchByTeams(game.homeTeam, game.awayTeam)
            ?.let {
                scoreboardWriteService.finishGame(it)
                println("Game was finished and removed from scoreboard")
            }
    }

    fun getSummary() = scoreboardReadService.getSummary()

    fun scoreboardActive() = scoreboardReadService.active()

    fun countMatches() =  scoreboardReadService.countMatches()
}