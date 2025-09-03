package com.example.colorseeker.domain.model

data class LeaderboardEntry(
    val userId: String,
    val username: String,
    val rank: Int,
    val score: Int, // SEEK balance or puzzle count
    val profileImageUrl: String? = null
)

enum class LeaderboardType {
    SEEK_HOLDERS,
    PUZZLE_UNLOCKS
}

data class Leaderboard(
    val type: LeaderboardType,
    val entries: List<LeaderboardEntry>,
    val userRank: Int? = null,
    val lastUpdated: Long = System.currentTimeMillis()
)