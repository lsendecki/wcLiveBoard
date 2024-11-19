package com.scoreservices

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class ScoreboardReadServiceTest {

    @Test
    fun shouldReadSomeMatches() {
        val dataSource = MundialInfoStorage()
        val scoreboardReadService = ScoreboardReadService(InMemoryTreeRepo(dataSource))
        Assertions.assertThat(scoreboardReadService.countMatches()).isZero()
    }
}