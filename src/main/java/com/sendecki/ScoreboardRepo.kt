package com.sendecki

typealias scoreStorage = MundialInfoStorage

object MundialInfoStorage {
    private val ongoingMatches = mutableSetOf<OngoingMatch>()
    fun getData() = ongoingMatches
    fun clean() = getData().clear()
}

sealed interface ScoreboardRepo {
    fun getStorage(): MutableCollection<OngoingMatch>
}

class InMemoryRepo(private val dataSource: MundialInfoStorage) : ScoreboardRepo {
    override fun getStorage(): MutableCollection<OngoingMatch> {
        return dataSource.getData()
    }
}
