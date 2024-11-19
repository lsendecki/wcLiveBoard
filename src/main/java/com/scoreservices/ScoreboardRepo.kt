package com.scoreservices

import java.util.TreeSet
import kotlin.math.sign

class MundialInfoStorage {

    private val matchesOrdering = { m1: OngoingMatch, m2:OngoingMatch ->
        if (m1.homeName == m2.homeName
            && m1.awayName == m2.awayName) {
            0
        } else if (m1.totalScore() == m2.totalScore()) {
            sign((m2.timeIndicator() - m1.timeIndicator()).toDouble()).toInt()
        } else {
            m2.totalScore() - m1.totalScore()
        }
    }

    private val ongoingMatchesTree = TreeSet(matchesOrdering)

    fun getTreeBasedData() = ongoingMatchesTree
    fun clean() {
        getTreeBasedData().clear()
    }
}

sealed interface ScoreboardRepo {
    fun getStorage(): MutableCollection<OngoingMatch>
}

class InMemoryTreeRepo(private val dataSource: MundialInfoStorage): ScoreboardRepo {
    override fun getStorage(): MutableCollection<OngoingMatch> {
        return dataSource.getTreeBasedData()
    }
}