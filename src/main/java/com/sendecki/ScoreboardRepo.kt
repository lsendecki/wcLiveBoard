package com.sendecki

typealias scoreStorage = MundialInfoStorage

object MundialInfoStorage {
    private val ongoingMatches = mutableListOf<OngoingMatch>()
    fun getData() = ongoingMatches
    fun clean() = getData().clear()
}

sealed interface ScoreboardRepo {
    fun getStorage(): MutableList<OngoingMatch>
}

class InMemoryRepo(private val dataSource: MundialInfoStorage) : ScoreboardRepo {
    override fun getStorage(): MutableList<OngoingMatch> {
        return dataSource.getData()
    }
}
