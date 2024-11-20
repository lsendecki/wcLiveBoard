package com.scoreboard

import com.scoreservices.Team

fun main(args: Array<String>) {
    val polishNationalDream = Team("Poland")
    val italyNationalDream = Team("Italy")
    val scoreboard = Scoreboard()
    scoreboard.addMatch(polishNationalDream, italyNationalDream)
    scoreboard.updateScore(
        newHomeResult = polishNationalDream.copy(goalsScored = 1),
        newAwayResult = italyNationalDream.copy(goalsScored = 2))
    print(scoreboard.getSummary())
}
