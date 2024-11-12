package com.sendecki

typealias mundialStore = Storage

object Storage {
    private val ongoingMatches = mutableListOf<OngoingMatch>()
    fun getData() = ongoingMatches
    fun clean() = getData().clear()
}

sealed interface ScoreboardRepo {
    fun getStorage(): MutableList<OngoingMatch>
}

class InMemoryRepo(private val dataSource: Storage) : ScoreboardRepo {
    override fun getStorage(): MutableList<OngoingMatch> {
        return dataSource.getData()
    }
}
