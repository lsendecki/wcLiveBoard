package com.sendecki

import org.assertj.core.api.Assertions
import org.junit.Assert
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ScoreboardTest {

    @Test
    fun shouldCreateSummaryForEmptyScoreboard() {
        val scoreboard = Scoreboard()
        Assertions.assertThat(scoreboard.getSummary()).isEmpty()
    }

    @Test
    fun shouldCreateSummaryForSingletonScoreboard() {
        val rpa = Team(name = "Republic of Southern Africa", currentScore = 3)
        val burkinaFaso = Team(name = "Burkina Faso", currentScore = 2)
        val scoreboard = Scoreboard(
            OngoingMatch(
                homeTeam = rpa,
                awayTeam = burkinaFaso,
                gameStartedAt = LocalDateTime.now()
            )
        )
        val summary = scoreboard.getSummary()
        Assertions.assertThat(summary).hasSize(1)
        Assertions.assertThat(summary).containsExactly(
            scoreboard.getMatchByTeams(rpa, burkinaFaso)!!
        )
    }

    @Test
    fun shouldAddMatchesToScoreboard() {
        val scoreboard = Scoreboard()
        scoreboard.addMatch(
            Team("Australia"),
            Team("Portugalia"))

        scoreboard.addMatch(
            Team("Hiszpania"),
            Team("Szwajcaria"))

        Assert.assertNotNull(scoreboard)
        Assert.assertTrue(scoreboard.active())
        Assert.assertEquals(2, scoreboard.matchesCount())
    }

    @Test
    fun shouldScoreBeUpdated() {
        val scoreboard = Scoreboard(ongoingMatches = initializeMatches())
        val homeTeam = Team("Niemcy", 3)
        val awayTeam = Team("Polska", 2)
        scoreboard.updateScore(homeTeam, awayTeam)
        val gameUpdated = scoreboard.getMatchByTeams(homeTeam, awayTeam)
        Assertions.assertThat(gameUpdated!!.homeScore()).isEqualTo(3)
        Assertions.assertThat(gameUpdated!!.awayScore()).isEqualTo(2)
    }

    @Test
    fun shouldAloneMatchBeFinished() {
        val scoreboard = Scoreboard()
        val ausie = Team("Australia")
        val portugal = Team("Portugalia")

        scoreboard.addMatch(
            ausie,
            portugal)

        Assertions.assertThat(scoreboard.active()).isTrue()
        scoreboard.finishGame(OngoingMatch(homeTeam = ausie, awayTeam = portugal))
        Assertions.assertThat(scoreboard.active()).isFalse()
    }

    @Test
    fun shouldSelectedMatchBeFinished() {
        val scoreboard = Scoreboard(initializeMatches())
        Assertions.assertThat(scoreboard.active()).isTrue()
        Assertions.assertThat(scoreboard.matchesCount()).isEqualTo(3)
        val niemcyPolskaMatch = scoreboard.getMatchByTeams(Team(name = "Niemcy"), Team(name = "Polska"))
        scoreboard.finishGame(niemcyPolskaMatch!!)
        Assertions.assertThat(scoreboard.active()).isTrue()
        Assertions.assertThat(scoreboard.matchesCount()).isEqualTo(2)
    }

    @Test
    fun shouldCreateSmallOrderedSummary() {
        val scoreboard = Scoreboard(ongoingMatches = initializeMatches())
        scoreboard.updateScore(
            Team(name = "Niemcy", currentScore = 3),
            Team(name = "Polska", currentScore = 0))
        val summary = scoreboard.getSummary()
        Assertions.assertThat(summary).isNotEmpty
        Assertions.assertThat(summary.first()).matches {
            it.homeTeam.name =="Niemcy" && it.awayTeam.name == "Polska"
        }
        Assertions.assertThat(summary.last()).matches {
            it.homeTeam.name =="Argentyna" && it.awayTeam.name == "Wlochy"
        }
    }
    
    @Test
    fun shouldCreateBiggerOrderedSummary() {
        val scoreboard = Scoreboard(ongoingMatches = initializeTournament())
        scoreboard.updateScore(
            Team(name = "Argentina", currentScore = 3),
            Team(name = "Australia", currentScore = 1))
        val summary = scoreboard.getSummary()
        Assertions.assertThat(summary).isNotEmpty
        Assertions.assertThat(summary).hasSize(5)
        Assertions.assertThat(summary).containsExactly(
            scoreboard.getMatchByTeams(Team("Uruguay"), Team("Italy"))!!,
            scoreboard.getMatchByTeams(Team("Spain"), Team("Brazil"))!!,
            scoreboard.getMatchByTeams(Team("Mexico"), Team("Canada"))!!,
            scoreboard.getMatchByTeams(Team("Argentina"), Team("Australia"))!!,
            scoreboard.getMatchByTeams(Team("Germany"), Team("France"))!!,

        )
        println(summary)
    }

    private fun initializeTournament(): MutableList<OngoingMatch> {
        return  mutableListOf(
            OngoingMatch(
                Team(name = "Mexico", currentScore = 0),
                Team(name = "Canada", currentScore = 5),
                LocalDateTime.of(2024, 6, 1, 10,0,0)),
            OngoingMatch(
                Team(name = "Spain", currentScore = 10),
                Team(name = "Brazil", currentScore = 2),
                LocalDateTime.of(2024, 6, 1, 11,0,0)),
            OngoingMatch(
                Team(name = "Germany", currentScore = 2),
                Team(name = "France", currentScore = 2),
                LocalDateTime.of(2024, 6, 1, 11,30,0)),
            OngoingMatch(
                Team(name = "Uruguay", currentScore = 6),
                Team(name = "Italy", currentScore = 6),
                LocalDateTime.of(2024, 6, 1, 12,30,0)),
            OngoingMatch(
                Team(name = "Argentina"),
                Team(name = "Australia"),
                LocalDateTime.of(2024, 6, 1, 13,30,0))
        )
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
                LocalDateTime.of(2024, 6, 1, 16,30,0)
            )
        )
    }
}