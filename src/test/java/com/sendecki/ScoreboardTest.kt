package com.sendecki

import org.assertj.core.api.Assertions
import org.junit.Assert
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ScoreboardTest {

    @Test
    fun shouldAddMatchesToScoreboard() {
        val scoreboard = Scoreboard(ongoingMatches = mutableListOf())
        scoreboard.addMatch(
            Team("Australia"),
            Team("Portugalia"),
            LocalDateTime.of(2024, 6, 1, 11,0,0))

        scoreboard.addMatch(
            Team("Hiszpania"),
            Team("Szwajcaria"),
            LocalDateTime.of(2024, 6, 1, 11,30,0))

        Assert.assertNotNull(scoreboard)
        Assert.assertTrue(scoreboard.active())
    }

    @Test
    fun shouldScoreBeUpdated() {
        val scoreboard = Scoreboard(ongoingMatches = initializeMatches())
        val homeTeam = Team("Niemcy", 3)
        val awayTeam = Team("Polska", 2)
        scoreboard.updateScore(homeTeam, awayTeam)
        val gameUpdated = scoreboard.getMatchByTeams(homeTeam, awayTeam)
        Assertions.assertThat(gameUpdated.homeScore()).isEqualTo(3)
        Assertions.assertThat(gameUpdated.awayScore()).isEqualTo(2)
    }

    @Test
    fun shouldCreateOrderedSummary() {
        val scoreboard = Scoreboard(ongoingMatches = initializeMatches())
        scoreboard.updateScore(Team("Niemcy", 3), Team("Polska", 0))
        val summary = scoreboard.getSummary()
        Assertions.assertThat(summary).isNotEmpty
        Assertions.assertThat(summary.first()).matches {
            it.homeTeam.name =="Niemcy" && it.awayTeam.name == "Polska"
        }
        Assertions.assertThat(summary.last()).matches {
            it.homeTeam.name =="Argentyna" && it.awayTeam.name == "Wlochy"
        }
    }

    private fun initializeMatches(): MutableList<OngoingMatch> {
        return mutableListOf(
            OngoingMatch(
                Team("Argentyna"),
                Team("Wlochy"),
                LocalDateTime.of(2024, 6, 1, 14,0,0)),
            OngoingMatch(
                Team("Niemcy"),
                Team("Polska"),
                LocalDateTime.of(2024, 6, 1, 12,0,0)),
            OngoingMatch(
                Team("Holandia"),
                Team("Szwecja"),
                LocalDateTime.of(2024, 6, 1, 16,30,0))
            )
    }
}