package com.scoreservices

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ScoreboardReadServiceTest {

    val niemcy = Team("Niemcy")
    val polska = Team("Polska")
    val holandia = Team("Holandia")
    val szwajcaria = Team("Szwajcaria")

    val scoreboardRepo = InMemoryTreeRepo(MundialInfoStorage())

    @BeforeEach
    fun initMatches() {
        scoreboardRepo.getStorage().add(OngoingMatch(niemcy, holandia))
        scoreboardRepo.getStorage().add(OngoingMatch(polska, szwajcaria))
    }

    @Test
    fun shouldReadAMatch() {
        val scoreboardReadService = ScoreboardReadService(scoreboardRepo)
        assertThat(scoreboardReadService.countMatches()).isNotZero()
        assertThat(scoreboardReadService.getMatchByTeams(niemcy, holandia)).isNotNull()
        assertThat(scoreboardReadService.getMatchByTeams(niemcy, holandia))
            .matches { it!!.homeName == "Niemcy" && it!!.awayName == "Holandia" }
    }
}