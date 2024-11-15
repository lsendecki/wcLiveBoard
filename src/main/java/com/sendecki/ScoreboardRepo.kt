package com.sendecki

import java.util.PriorityQueue
import java.util.TreeSet
import kotlin.math.sign

typealias scoreStorage = MundialInfoStorage

object MundialInfoStorage {

    private val matchesOrdering = {
            m1: OngoingMatch, m2:OngoingMatch ->
        if (m1.homeTeam.name == m2.homeTeam.name
            && m1.awayTeam.name == m2.awayTeam.name) {
            0
        } else if (m1.totalScore() == m2.totalScore()) {
            sign((m2.timeIndicator() - m1.timeIndicator()).toDouble()).toInt()
        } else {
            m2.totalScore() - m1.totalScore()
        }
    }

    private val ongoingMatches = mutableSetOf<OngoingMatch>()
    private val ongoingMatchesQueue = PriorityQueue(matchesOrdering)
    private val ongoingMatchesTree = TreeSet(matchesOrdering)

    fun getQueueBasedData() = ongoingMatchesQueue
    fun getSetBasedData() = ongoingMatches
    fun genTreeBasedData() = ongoingMatchesTree
    fun clean() {
        getSetBasedData().clear()
        getQueueBasedData().clear()
        genTreeBasedData().clear()
    }
}

sealed interface ScoreboardRepo {
    fun getStorage(): MutableCollection<OngoingMatch>
}

class InMemoryRepo(private val dataSource: MundialInfoStorage) : ScoreboardRepo {
    override fun getStorage(): MutableCollection<OngoingMatch> {
        return dataSource.getSetBasedData()
    }
}

class InMemoryQueueRepo(private val dataSource: MundialInfoStorage): ScoreboardRepo {
    override fun getStorage(): MutableCollection<OngoingMatch> {
        return dataSource.getQueueBasedData()
    }
}

class InMemoryTreeRepo(private val dataSource: MundialInfoStorage): ScoreboardRepo {
    override fun getStorage(): MutableCollection<OngoingMatch> {
        return dataSource.genTreeBasedData()
    }
}