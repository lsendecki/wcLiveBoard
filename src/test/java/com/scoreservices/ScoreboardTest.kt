package com.scoreservices

import com.scoreboard.Scoreboard
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class ScoreboardTest {

    @Test
    fun shouldHandleMultipleScoreboards() {
        val s1 = Scoreboard()
        s1.addMatch(Team("Anglia"), Team("Walia"))
        s1.addMatch(Team("Polska"), Team("Grecja"))
        Assertions.assertThat(s1.getSummary()).hasSize(2)

        val s2 = Scoreboard()
        s2.addMatch(Team("Holandia"), Team("Dania"))
        Assertions.assertThat(s2.getSummary()).hasSize(1)
        Assertions.assertThat(s1.getSummary()).hasSize(2)
    }
}