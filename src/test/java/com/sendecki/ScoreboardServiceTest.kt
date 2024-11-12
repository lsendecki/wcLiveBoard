package com.sendecki

import org.assertj.core.api.Assertions.*
import org.junit.Assert.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ScoreboardServiceTest {

    @BeforeEach
    fun beforeEach() {
        scoreStorage.clean();
    }

    @Test
    fun shouldCreateSummaryForEmptyScoreboard() {
        val scoreboardService = ScoreboardService(
            ScoreboardReadService(InMemoryRepo(scoreStorage)),
            ScoreboardWriteService(InMemoryRepo(scoreStorage)))
        assertThat(scoreboardService.getSummary()).isEmpty()
    }

    @Test
    fun shouldCreateSummaryForSingletonScoreboard() {
        val scoreboardReadService = ScoreboardReadService(InMemoryRepo(scoreStorage))
        val scoreboardWriteService = ScoreboardWriteService(InMemoryRepo(scoreStorage))
        val scoreboardService = ScoreboardService(
            scoreboardReadService,
            scoreboardWriteService
        )
        val rpa = Team(name = "Republic of Southern Africa", goalsScored = 3)
        val burkinaFaso = Team(name = "Burkina Faso", goalsScored = 2)
        scoreboardService.addMatch(
            homeTeam = rpa,
            awayTeam = burkinaFaso)

        val summary = scoreboardService.getSummary()
        assertThat(summary).hasSize(1)
        assertThat(summary).containsExactly(
            scoreboardReadService.getMatchByTeams(rpa, burkinaFaso)!!
        )
    }

    @Test
    fun shouldAddMatchesToScoreboard() {
        val scoreboardService = ScoreboardService(
            ScoreboardReadService(InMemoryRepo(scoreStorage)),
            ScoreboardWriteService(InMemoryRepo(scoreStorage))
        )
        scoreboardService.addMatch(
            Team("Australia"),
            Team("Portugalia"))

        scoreboardService.addMatch(
            Team("Hiszpania"),
            Team("Szwajcaria"))

        assertNotNull(scoreboardService)
        assertTrue(scoreboardService.active())
        assertEquals(2, scoreboardService.countMatches())
    }

    @Test
    fun shouldScoreBeUpdated() {
        val scoreboardReadService = ScoreboardReadService(InMemoryRepo(scoreStorage))
        val scoreboardService = ScoreboardService(
            scoreboardReadService,
            ScoreboardWriteService(InMemoryRepo(scoreStorage))
        )
        initializeMatches().forEach { scoreboardService.addMatch(it.first, it.second, it.third) }
        val homeTeam = Team("Niemcy", 3)
        val awayTeam = Team("Polska", 2)
        scoreboardService.updateScore(homeTeam, awayTeam)
        val gameUpdated = scoreboardReadService.getMatchByTeams(homeTeam, awayTeam)
        assertThat(gameUpdated!!.homeScore()).isEqualTo(3)
        assertThat(gameUpdated!!.awayScore()).isEqualTo(2)
    }

    @Test
    fun shouldAloneMatchBeFinished() {
        val scoreboardService = ScoreboardService(
            ScoreboardReadService(InMemoryRepo(scoreStorage)),
            ScoreboardWriteService(InMemoryRepo(scoreStorage))
        )
        val ausie = Team("Australia")
        val portugal = Team("Portugalia")

        scoreboardService.addMatch(
            ausie,
            portugal)

        assertThat(scoreboardService.active()).isTrue()
        scoreboardService.finishGame(OngoingMatch(homeTeam = ausie, awayTeam = portugal))
        assertThat(scoreboardService.active()).isFalse()
    }

    @Test
    fun shouldSelectedMatchBeFinished() {
        val scoreboardReadService = ScoreboardReadService(InMemoryRepo(scoreStorage))
        val scoreboardService = ScoreboardService(
            scoreboardReadService,
            ScoreboardWriteService(InMemoryRepo(scoreStorage))
        )
        initializeMatches().forEach { scoreboardService.addMatch(it.first, it.second, it.third) }

        assertThat(scoreboardService.active()).isTrue()
        assertThat(scoreboardService.countMatches()).isEqualTo(3)
        val niemcyPolskaMatch = scoreboardReadService.getMatchByTeams(Team(name = "Niemcy"), Team(name = "Polska"))
        scoreboardService.finishGame(niemcyPolskaMatch!!)
        assertThat(scoreboardService.active()).isTrue()
        assertThat(scoreboardService.countMatches()).isEqualTo(2)
    }

    @Test
    fun shouldCreateSmallOrderedSummary() {
        val scoreboardService = ScoreboardService(
            ScoreboardReadService(InMemoryRepo(scoreStorage)),
            ScoreboardWriteService(InMemoryRepo(scoreStorage))
        )
        initializeMatches().forEach { scoreboardService.addMatch(it.first, it.second, it.third) }
        scoreboardService.updateScore(
            Team(name = "Niemcy", goalsScored = 3),
            Team(name = "Polska", goalsScored = 0))
        val summary = scoreboardService.getSummary()
        assertThat(summary).isNotEmpty
        assertThat(summary.first()).matches {
            it.homeTeam.name =="Niemcy" && it.awayTeam.name == "Polska"
        }
        assertThat(summary.last()).matches {
            it.homeTeam.name =="Argentyna" && it.awayTeam.name == "Wlochy"
        }
    }
    
    @Test
    fun shouldCreateBiggerOrderedSummary() {
        val scoreboardReadService = ScoreboardReadService(InMemoryRepo(scoreStorage))
        val scoreboardService = ScoreboardService(
            scoreboardReadService,
            ScoreboardWriteService(InMemoryRepo(scoreStorage))
        )
        initializeTournament().forEach { scoreboardService.addMatch(it.first, it.second, it.third) }
        scoreboardService.updateScore(
            Team(name = "Argentina", goalsScored = 3),
            Team(name = "Australia", goalsScored = 1))
        val summary = scoreboardService.getSummary()
        assertThat(summary).isNotEmpty
        assertThat(summary).hasSize(5)
        assertThat(summary).containsExactly(
            scoreboardReadService.getMatchByTeams(Team("Uruguay"), Team("Italy"))!!,
            scoreboardReadService.getMatchByTeams(Team("Spain"), Team("Brazil"))!!,
            scoreboardReadService.getMatchByTeams(Team("Mexico"), Team("Canada"))!!,
            scoreboardReadService.getMatchByTeams(Team("Argentina"), Team("Australia"))!!,
            scoreboardReadService.getMatchByTeams(Team("Germany"), Team("France"))!!,

        )
        println(summary)
    }

    private fun initializeTournament(): List<Triple<Team, Team, LocalDateTime>> {
        return  listOf(
            Triple(
                Team(name = "Mexico", goalsScored = 0),
                Team(name = "Canada", goalsScored = 5),
                LocalDateTime.of(2024, 6, 1, 10,0,0)),
            Triple(
                Team(name = "Spain", goalsScored = 10),
                Team(name = "Brazil", goalsScored = 2),
                LocalDateTime.of(2024, 6, 1, 11,0,0)),
            Triple(
                Team(name = "Germany", goalsScored = 2),
                Team(name = "France", goalsScored = 2),
                LocalDateTime.of(2024, 6, 1, 11,30,0)),
            Triple(
                Team(name = "Uruguay", goalsScored = 6),
                Team(name = "Italy", goalsScored = 6),
                LocalDateTime.of(2024, 6, 1, 12,30,0)),
            Triple(
                Team(name = "Argentina"),
                Team(name = "Australia"),
                LocalDateTime.of(2024, 6, 1, 13,30,0))
        )
    }

    private fun initializeMatches(): List<Triple<Team, Team, LocalDateTime>> {
        return listOf(
            Triple(
                Team("Argentyna"),
                Team("Wlochy"),
            LocalDateTime.of(2024, 6, 1, 14,0,0)),
            Triple(
                Team("Niemcy"),
                Team("Polska"),
                LocalDateTime.of(2024, 6, 1, 12,0,0)),
            Triple(
                Team("Holandia"),
                Team("Szwecja"),
                LocalDateTime.of(2024, 6, 1, 16,30,0)
            )
        )
    }
}