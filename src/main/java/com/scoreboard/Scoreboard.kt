package com.scoreboard

import com.scoreservices.*
import java.time.LocalDateTime
import java.util.*

class Scoreboard {

    private val scoreboardRepo = InMemoryTreeRepo(scoreStorage)

    private val scoreboardService = ScoreboardService(
        ScoreboardReadService(scoreboardRepo),
        ScoreboardWriteService(scoreboardRepo)
    )

    fun addMatch(homeTeam: Team, awayTeam: Team, startsAt: LocalDateTime = LocalDateTime.now()) {
        scoreboardService.addMatch(homeTeam, awayTeam, startsAt)
    }

    fun updateScore(newHomeResult: Team, newAwayResult: Team) {
        scoreboardService.updateScore(newHomeResult, newAwayResult)
    }

    fun finishGame(game: OngoingMatch) {
        scoreboardService.finishGame(game)
    }

    fun getSummary(): Collection<OngoingMatch> {
        return Collections.unmodifiableCollection(scoreboardService.getSummary())
    }
}