package com.example.colorseeker.domain.repository

import com.example.colorseeker.domain.model.Leaderboard
import com.example.colorseeker.domain.model.LeaderboardType
import kotlinx.coroutines.flow.Flow

interface LeaderboardRepository {
    suspend fun getLeaderboard(type: LeaderboardType): Flow<Leaderboard>
    suspend fun getUserRank(userId: String, type: LeaderboardType): Int?
    suspend fun refreshLeaderboards(): Result<Boolean>
}